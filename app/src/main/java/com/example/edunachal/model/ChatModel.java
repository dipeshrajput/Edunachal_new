package com.example.edunachal.model;

public class ChatModel {
    String message;
    String from;
    String type;
    String messageId;
    long timestamp;
    String uid;

    public ChatModel(String message, String from, String type, String messageId, long timestamp, String uid) {
        this.message = message;
        this.from = from;
        this.type = type;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
