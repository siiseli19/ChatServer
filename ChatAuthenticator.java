package com.sami.chatserver;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

import com.sun.net.httpserver.BasicAuthenticator;

public class ChatAuthenticator extends BasicAuthenticator {

    private Map<String, User> users = null;
    

    public ChatAuthenticator() {
        super("chat");
        users = new Hashtable<String, User>();
        users.put(User, "passwd");
        //TODO Auto-generated constructor stub
    }

    
 


    @Override
    public boolean checkCredentials(String username, String password) {
        
        if(users.containsKey(username)) {
            if(users.get(username).equals(password)) {
                return true;
            }


        }
        return false;
    }



    
}
