package com.sami.chatserver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;




public class ChatHandler implements HttpHandler {
    private String responseBody = "";
    private ArrayList<String> messages = new ArrayList<String>(); 
    
    //uudelleenmääritys
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int code = 200;
        try {
            if(exchange.getRequestMethod().equalsIgnoreCase("POST")){
                code = handleChatMessageFromClient(exchange);
            }else{
                code = 400;
                responseBody = "Not Supported!";
            }
        } catch (IOException e) {
            code = 500;
            responseBody = "Error in handling the request: " + e.getMessage();
        } catch (Exception e) {
            code = 500;
            responseBody = "Server error: " + e.getMessage();
        }
        if (code < 200 || code > 299) {
           byte [] bytes = responseBody.getBytes("UTF-8");
           exchange.sendResponseHeaders(code, bytes.length);
           OutputStream os = exchange.getResponseBody();
           os.write(bytes);
           os.close();
        }
            

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            // Handle POST requests (client sent new chat message)
            InputStream input = exchange.getRequestBody();
            String text = new BufferedReader(new InputStreamReader (input,
                                    StandardCharsets.UTF_8))
                                    .lines()
                                    .collect(Collectors.joining("\n"));
            
           try{

            JSONObject registrationMsg = new JSONObject(text);
            registrationMsg.getString(text);
            JSONObject username = new JSONObject();
            JSONObject password = new JSONObject();
            JSONObject email = new JSONObject();
            
            if(username.equals(username)){
                
            }

            //tarkistaa jos JSON ei ole validi
           
           }catch (JSONException e){
             code = 400;
             responseBody = "Not Valid";
           }
            messages.add(text);
            input.close();
            exchange.sendResponseHeaders(200, -1);
            } else if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            // Handle GET request (client wants to see all messages)                    
            String messageBody = "";
            for (String message : messages){
                messageBody += message + "\n";                    
            }
            
            // String response = "kö kö kä This is �!";
            byte [] bytes = messageBody.getBytes("UTF-8");
            //parametrit: statuskoodi, byte pituus        
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream stream = exchange.getResponseBody();
            stream.write(bytes);
            stream.close();
          
        }
    }

    private int handleChatMessageFromClient(HttpExchange exchange) throws Exception{
        int code = 200;

        Headers headers = exchange.getRequestHeaders();
        int contentLenght = 0;
        String contentType = "";
        if(headers.containsKey("Content-Lenght")){
            contentLenght = Integer.parseInt(headers.get("Content-Lenght").get(0));
        }else{
            code = 411;
            return code;
        }
        if(headers.containsKey("Content-Type")){
            contentType = headers.get("Content-Type").get(0);
        }else{
            code = 400;
            responseBody = "No content type in request";
            return code;
        }
        if(contentType.equalsIgnoreCase("application/json")){ 
            InputStream stream = exchange.getRequestBody();
            String text = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));
            ChatServer.log(text);
            stream.close();
           if(text.trim().length() > 0){
               processMessage(text);
               exchange.sendResponseHeaders(code, -1);
               ChatServer.log("New chatmessage saved");
            }else{
                code = 400;
                responseBody = "No content in request";
                ChatServer.log(responseBody);
            }
        }else{
            code = 411;
            responseBody = "Content-Type must be application/json";
            ChatServer.log(responseBody);
        }
        return code;
        
    }

    private void processMessage(String text){
        
        messages.add(text);
    }
}
