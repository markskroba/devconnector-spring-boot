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
}
