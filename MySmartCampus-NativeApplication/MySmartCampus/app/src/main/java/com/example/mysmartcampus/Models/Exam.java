package com.example.mysmartcampus.Models;

import java.util.ArrayList;
import java.util.Date;

public class Exam{

    private Integer id;
    private String examtitle;
    private String hash;
    private ArrayList<Grade> grades = new ArrayList<>();

    public Exam(Integer id, String examtitle, String hash, ArrayList<Grade> grades) {
        this.id = id;
        this.examtitle = examtitle;
        this.hash = hash;
        this.grades = grades;
    }

    public Integer getExamId() {
        return id;
    }

    public String getExamTitle() {
        return examtitle;
    }

    public String getExamHash() {
        return hash;
    }

    public ArrayList<Grade> getExamGrades() {
        return grades;
    }

    public void setExamGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

}
