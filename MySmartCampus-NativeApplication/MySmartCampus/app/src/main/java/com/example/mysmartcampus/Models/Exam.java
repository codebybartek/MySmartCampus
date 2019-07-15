package com.example.mysmartcampus.Models;

import java.util.ArrayList;
import java.util.Date;

public class Exam{

    private String examtitle;
    private String examDate;
    private char examGrade;
    private Student student;

    public Exam(String examtitle, String examDate, char examGrade, Student student) {
        this.examtitle = examtitle;
        this.examDate = examDate;
        this.examGrade = examGrade;
        this.student = student;
    }

    public String getExamTitle() {
        return examtitle;
    }

    public String getExamDate() {
        return examDate;
    }

    public int getExamGrade() {
        return examGrade;
    }

    public Student getStudent() {
        return student;
    }

}
