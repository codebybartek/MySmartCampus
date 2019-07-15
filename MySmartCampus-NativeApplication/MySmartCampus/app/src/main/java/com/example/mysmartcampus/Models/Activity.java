package com.example.mysmartcampus.Models;

import java.util.ArrayList;
import java.util.Date;

public class Activity extends ArrayList<Activity> {

    private String title;
    private String activityDate;
    private String hour;
    private int duration;
    private int checked;
    private ArrayList<Student> studentsPresence = new ArrayList<>();

    public Activity(String title, String activityDate, String hour, int duration, int checked) {
        this.title = title;
        this.activityDate = activityDate;
        this.hour = hour;
        this.duration = duration;
        this.checked = checked;
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

    public void setStudentsPresence(ArrayList<Student> studentsPresence) {
        this.studentsPresence = studentsPresence;
    }

}
