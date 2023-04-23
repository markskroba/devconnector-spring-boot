package com.markskroba.devconnectorspringboot.posts;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class CommentData {
    private String user;
    private String text;
    private String name;
    private String avatar;
    private Date date;

    public CommentData(String text, String user) {
        this.user = user;
        this.text = text;
        this.name = "Test comment";
        this.avatar = "Test avatar";
        this.date = new Date();
    }
}
