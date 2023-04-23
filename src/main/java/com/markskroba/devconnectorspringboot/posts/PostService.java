package com.markskroba.devconnectorspringboot.posts;

import com.markskroba.devconnectorspringboot.posts.dto.CreatePostDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();

    Optional<Post> findById(String id);

    Post deleteOneById(Post post);

    Post createPost(CreatePostDto dto);

    Post likePost(Post post);

    Post unlikePost(Post post);

    Post commentOnPost(String id, CreatePostDto dto);

    Post removeCommentOnPost(String id, String commentId);
}
