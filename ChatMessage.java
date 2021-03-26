package com.sami.chatserver;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class ChatMessage {
    public LocalDateTime sent;
    public String username;
    public String message;

    public ChatMessage(){

}
    ChatMessage (String username, String message, LocalDateTime sent) {
        this.username = username;
        this.message = message;
        this.sent = sent;
        }
        public String getUsername2(){
            return username;
        }
        public String getMessage(){
            return message;
        }
        public LocalDateTime getSent(){
            return sent;
        }

    long dateAsInt() {
        return sent.toInstant(ZoneOffset.UTC).toEpochMilli();
        }
    void setSent(long epoch) {
        sent = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
        }
       }


