package com.markskroba.devconnectorspringboot.posts;

import com.markskroba.devconnectorspringboot.posts.dto.CreatePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> findAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findOneById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDto dto) {
        return new ResponseEntity<>(postService.createPost(dto), HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<ResponseMessage> deleteOneById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(postService.deleteOneById(id));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<Post> likePost(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(postService.likePost(id));
    }

    @PutMapping("/unlike/{id}")
    public ResponseEntity<Post> unlikePost(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(postService.unlikePost(id));
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<Post> commentOnPost(@PathVariable("id") String id, @RequestBody CreatePostDto dto) {
        return ResponseEntity.ok().body(postService.commentOnPost(id, dto));
    }

    @DeleteMapping("/comment/{id}/{comment_id}")
    public ResponseEntity<Post> removeCommentOnPost(@PathVariable("id") String id, @PathVariable("comment_id") String commentId) {
        return ResponseEntity.ok().body(postService.removeCommentOnPost(id, commentId));
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseMessage> handleNotFound(Exception e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        if (e.getClass() == IllegalArgumentException.class) httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ResponseMessage(e.getMessage()), httpStatus);
    }
}
