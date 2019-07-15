package com.example.mysmartcampus.Models;

import java.util.ArrayList;

public class Professor {

    private String name;
    private String email;
    private String tagId;
    private String tagData;

    public Professor(String name, String email, String tagId, String tagData, ArrayList<Subject> subjects) {
        this.name = name;
        this.email = email;
        this.tagId = tagId;
        this.tagData = tagData;
    }

    public void setTagId(String tagId) {
        tagId = tagId;
    }

    public void setTagData(String tagData) {
        this.tagData = tagData;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagData() { return tagData; }

}
