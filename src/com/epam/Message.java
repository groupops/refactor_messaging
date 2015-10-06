package com.epam;

/**
 * Created by Dmytro_Ulanovych on 10/5/2015.
 */
public class Message {
    private int id;
    private String body;

    public Message(int id, String body) {
        this.body = body;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}