package com.example.edunachal.model;

public class PdfModel {
    String name, storageName, url;

    public PdfModel(String name, String storageName, String url) {
        this.name = name;
        this.storageName = storageName;
        this.url = url;
    }

    public PdfModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoragename() {
        return storageName;
    }

    public void setStoragename(String storageName) {
        this.storageName = storageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
