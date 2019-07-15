package com.example.mysmartcampus.Models;

import java.util.Date;

public class News {
    private String title;
    private String newsDate;
    private String info;
    private String newsPicture;

    public News(String title, String newsDate, String info, String newsPicture) {
        this.title = title;
        this.newsDate = newsDate;
        this.info = info;
        this.newsPicture = newsPicture;
    }

    public String getTitle() {
        return title;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getInfo() {
        return info;
    }

    public String getNewsPicture() {
        return newsPicture;
    }
}
