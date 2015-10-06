package com.epam.messages;

public class Message {

    private String content;
    private int id;
    
    public Message(int id, String content){
        this.id = id;
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
