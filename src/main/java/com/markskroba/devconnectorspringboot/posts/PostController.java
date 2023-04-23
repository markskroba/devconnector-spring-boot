package com.markskroba.devconnectorspringboot.posts;

import com.markskroba.devconnectorspringboot.posts.dto.CreatePostDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDto dto) {
        return new ResponseEntity<>(postService.createPost(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findOneById(@PathVariable("id") String id) {
        return postService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Post> deleteOneById(@PathVariable("id") String id) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(postService.deleteOneById(post.get()));
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<Post> likePost(@PathVariable("id") String id) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()) return ResponseEntity.notFound().build();
        Post res = postService.likePost(post.get());
        if (res == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/unlike/{id}")
    public ResponseEntity<Post> unlikePost(@PathVariable("id") String id) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()) return ResponseEntity.notFound().build();
        Post res = postService.unlikePost(post.get());
        if (res == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<Post> commentOnPost(@PathVariable("id") String id, @RequestBody CreatePostDto dto) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()) return ResponseEntity.notFound().build();
        Post res = postService.commentOnPost(post.get().get_id(), dto);
        if (res == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/comment/{id}/{comment_id}")
    public ResponseEntity<Post> removeCommentOnPost(@PathVariable("id") String id, @PathVariable("comment_id") String comment_id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
           Post post = optionalPost.get();
           Post res = postService.removeCommentOnPost(id, comment_id);
           if (!(res == null)) {
               return ResponseEntity.ok().body(res);
           } else {
               return ResponseEntity.notFound().build();
           }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
