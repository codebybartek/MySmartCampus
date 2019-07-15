package com.example.mysmartcampus.Models;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String mobileToken;
    private String tagId;
    private String photo_src;
    private ArrayList<Subject> subjects = new ArrayList<>();

    public User(String name, String email, String mobileToken, String tagId, String photo_src, ArrayList<Subject> subjects) {
        this.name = name;
        this.email = email;
        this.mobileToken = mobileToken;
        this.tagId = tagId;
        this.photo_src = photo_src;
        this.subjects = subjects;
    }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getMobileToken() {
        return mobileToken;
    }

    public String getTagId() {
        return tagId;
    }

    public String getPhoto_src() {
        return photo_src;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject){
        this.subjects.add(subject);
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }


}
