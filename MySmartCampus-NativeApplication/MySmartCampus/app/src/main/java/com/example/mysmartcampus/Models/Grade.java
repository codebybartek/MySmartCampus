package com.example.mysmartcampus.Models;

import java.util.ArrayList;

public class Grade {
    private Integer id;
    private String grade_date;
    private Integer grade;
    private String userName;
    private String userId;

    public Grade(String grade_date, Integer grade, String userId, String userName) {
        this.grade_date = grade_date;
        this.grade = grade;
        this.userId = userId;
        this.userName = userName;
    }

    public String getGradeDate() {
        return grade_date;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getGradeUserId() {
        return userId;
    }

    public String getGradeUserName() {
        return userName;
    }
}
