package com.sami.chatserver;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import com.sami.chatserver.ChatHandler;
import java.time.LocalDateTime;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsParameters;



public class ChatServer 
{
    public static void main( String[] args )
    {
        try {
            
        HttpsServer server = HttpsServer.create(new InetSocketAddress(9090),0 );
        SSLContext sslContext = chatServerSSLContext();
        server.setHttpsConfigurator (new HttpsConfigurator(sslContext) {
            public void configure (HttpsParameters params) {
            InetSocketAddress remote = params.getClientAddress();
            SSLContext c = getSSLContext();
            SSLParameters sslparams = c.getDefaultSSLParameters();
            params.setSSLParameters(sslparams);
            }
            });
        ChatAuthenticator auth = new ChatAuthenticator();
        HttpContext chatContext = server.createContext("/chat", new ChatHandler());
        chatContext.setAuthenticator(auth);
        server.setExecutor(null);
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server is running...");




   


    } catch (Exception e) {
        //TODO: handle exception
    } } 

    private static SSLContext chatServerSSLContext() throws IOException, CertificateException,KeyStoreException, NoSuchAlgorithmException, FileNotFoundException, KeyManagementException, UnrecoverableEntryException {

        char[] passphrase = "anskuonhomo".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore.jks"), passphrase);
     
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);
     
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
     
        SSLContext ssl = SSLContext.getInstance("TLS");
        ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return ssl;



    }

    public static void log(String responseBody) {
    }













}

