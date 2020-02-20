package com.example.mysmartcampus.Models;

import java.util.ArrayList;
import java.util.Date;

public class Activity{

    private Integer id;
    private String title;
    private String activityDate;
    private String hour;
    private String hash;
    private int duration;
    private int checked;
    private ArrayList<Student> studentsPresence = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();

    public Activity(Integer id, String title, String activityDate, String hour, String hash, int duration, int checked) {
        this.id = id;
        this.title = title;
        this.activityDate = activityDate;
        this.hour = hour;
        this.hash = hash;
        this.duration = duration;
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public String getHour() {
        return hour;
    }

    public String getHash() {
        return hash;
    }

    public int getDuration() {
        return duration;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked() {
        this.checked = 1;
    }

    public ArrayList<Student> getStudentsPresence() {
        return studentsPresence;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudentsPresence(ArrayList<Student> studentsPresence) {
        this.studentsPresence = studentsPresence;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

}
