package org.kehrbusch.entities;

public class MessageResponseApi {
    private String message;

    public MessageResponseApi() {}

    public MessageResponseApi(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
