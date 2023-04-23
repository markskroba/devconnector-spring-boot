package com.markskroba.devconnectorspringboot.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("posts")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private String _id;
    private String text;
    private String name;
    private String avatar;
    private String user;
    private List<LikeData> likes;
    private List<CommentData> comments;
    private Date date;

    public Post(String text, String user) {
       this.text = text;
       // todo: once token is stored in headers, dto should get it and use for name/avatar here
       this.name = "Mark Skroba";
       this.avatar = "//www.gravatar.com/avatar/5a156282f2f4fe6318bdaef95f1f2d4f?s=200&r=pg&d=mm";
       this.user = "6056d8958ba84fc81f46cfe7";
       this.likes = new ArrayList<>();
       this.comments = new ArrayList<>();
       this.date = new Date();
    }
}
