package com.markskroba.devconnectorspringboot.posts;

import com.markskroba.devconnectorspringboot.posts.dto.CreatePostDto;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post findById(String id);

     ResponseMessage deleteOneById(String id);

    Post createPost(CreatePostDto dto);

    Post likePost(String id);

    Post unlikePost(String id);

    Post commentOnPost(String id, CreatePostDto dto);

    Post removeCommentOnPost(String id, String commentId);
}
