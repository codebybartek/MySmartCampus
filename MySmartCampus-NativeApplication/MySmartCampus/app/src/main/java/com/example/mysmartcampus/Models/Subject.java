package com.example.mysmartcampus.Models;

import java.util.ArrayList;

public class Subject {

    private String title;
    private ArrayList<Course> courses = new ArrayList<>();

    public Subject(String name,  ArrayList<Course> courses) {
        this.title = name;
        this.courses = courses;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
