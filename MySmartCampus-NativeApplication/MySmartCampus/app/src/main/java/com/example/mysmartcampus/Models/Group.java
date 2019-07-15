package com.example.mysmartcampus.Models;

import java.util.ArrayList;

public class Group {

    private String title;
    private ArrayList<Student> students = new ArrayList<>();

    public String getName() {
        return title;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Group(String name, ArrayList<Student> students) {
        this.title = name;
        this.students = students;
    }
}
