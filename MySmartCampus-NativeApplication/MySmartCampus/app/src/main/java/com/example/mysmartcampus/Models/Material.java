package com.example.mysmartcampus.Models;

import java.util.Date;

public class Material {
    private String title;
    private String materialDate;
    private String attachment;

    public Material(String title, String materialDate, String attachment) {
        this.title = title;
        this.materialDate = materialDate;
        this.attachment = attachment;
    }

    public String getTitle() {
        return title;
    }

    public String getMaterialDate() {
        return materialDate;
    }

    public String getAttachment() {
        return attachment;
    }

}
