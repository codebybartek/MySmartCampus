package com.example.mysmartcampus.Models;

import java.util.ArrayList;

public class Course {

    private String title;
    private Group group;
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<News> news = new ArrayList<>();
    private ArrayList<Material> materials = new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();

    public Course(String name, Group group) {
        this.title = name;
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity){
        this.activities.add(activity);
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public void addNews(News newsToAdd){
        this.news.add(newsToAdd);
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Material> materials) {
        this.materials = materials;
    }

    public void addMaterial(Material material){
        this.materials.add(material);
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public void addExam(Exam exam){
        this.exams.add(exam);
    }
}
