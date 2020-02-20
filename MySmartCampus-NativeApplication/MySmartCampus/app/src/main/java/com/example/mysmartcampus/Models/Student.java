package com.example.mysmartcampus.Models;

public class Student {

    private Integer id;
    private String name;
    private String email;
    private String hash;
    //private String index;
    private String tagId;
    private String tagDate;
    public boolean status;

    public Student(Integer id, String name, String email, String hash, String tagId, boolean status, String tagDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hash = hash;
        this.status = status;
        this.tagId = tagId;
        this.tagDate = tagDate;
    }

    public void setTagId(String tagId) {
        tagId = tagId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHash() { return hash; }

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
