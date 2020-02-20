package com.example.mysmartcampus.Models;

import java.util.Date;

public class News {
    private String title;
    private String newsDate;
    private String content;
    private String newsPicture;

    public News(String title, String newsDate, String content, String newsPicture) {
        this.title = title;
        this.newsDate = newsDate;
        this.content = content;
        this.newsPicture = newsPicture;
    }

    public String getNewsTitle() {
        return title;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsContent() {
        return content;
    }

    public String getNewsPicture() {
        return newsPicture;
    }
}
