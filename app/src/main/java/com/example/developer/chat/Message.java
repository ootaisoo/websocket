package com.example.developer.chat;

public class Message {

    private String roomName;
    private String message;

    public Message(String roomName, String message) {
        this.roomName = roomName;
        this.message = message;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getMessage() {
        return message;
    }


}
