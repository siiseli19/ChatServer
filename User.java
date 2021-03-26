package com.sami.chatserver;

public class User {

    private String username;
    private String password;
    private String email;

    User (String username, String password, String email){
            this.username = username;
            this.password = password;
            this.email = email;
    }
    public String getUsername(){
            return username;
    }
    public String getPassword(){
            return password;
    }
    public String getEmail(){
            return email;
    }


}
