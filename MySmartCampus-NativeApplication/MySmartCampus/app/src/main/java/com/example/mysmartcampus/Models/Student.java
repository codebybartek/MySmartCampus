package com.example.mysmartcampus.Models;

public class Student {

    private String name;
    private String email;
    private String index;
    private String tagId;
    private String tagDate;
    public boolean status;

    public Student(String name, String email, String index, String tagId, boolean status, String tagDate) {
        this.name = name;
        this.email = email;
        this.index = index;
        this.status = status;
        this.tagId = tagId;
        this.tagDate = tagDate;
    }

    public void setTagId(String tagId) {
        tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getIndex() {
        return index;
    }

    public String getTagId() {
        return tagId;
    }

    public boolean getStatus() {
        return status;
    }

    public String getTagDate() { return tagDate; }

    public void setTagDate(String tagDate) {
        this.tagDate = tagDate;
    }

}
