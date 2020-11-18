package com.example.edunachal.model;

public class CurrentAffairsModel {
    String title,content,tag;
    long timestamp;

    public CurrentAffairsModel() {
    }

    public CurrentAffairsModel(String title, String content, String tag, long timestamp) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
