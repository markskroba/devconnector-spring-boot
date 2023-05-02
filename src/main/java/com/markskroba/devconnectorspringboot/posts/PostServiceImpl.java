package com.markskroba.devconnectorspringboot.posts;

import com.markskroba.devconnectorspringboot.auth.jwt.JwtAuthService;
import com.markskroba.devconnectorspringboot.posts.dto.CreatePostDto;
import com.markskroba.devconnectorspringboot.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final JwtAuthService authService;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post not found"));
    }

    @Override
    public ResponseMessage deleteOneById(String id) {
        Post p = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post not found"));
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        if (Objects.equals(user.get_id(), p.getUser())) throw new NoSuchElementException("No post from this user with this ID exist");
        postRepository.deleteById(id);
        return new ResponseMessage("Post deleted");
    }

    @Override
    public Post createPost(CreatePostDto dto) {
        if (dto.getText() == null) throw new IllegalArgumentException("Text is required");
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        Post post = Post
                .builder()
                .text(dto.getText())
                .name(user.getName())
                .avatar(user.getAvatar())
                .user(user.get_id())
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .date(new Date())
                .build();

        postRepository.save(post);
        return post;
    }

    @Override
    public Post likePost(String id) {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String userId = user.get_id();
        Post post = this.findById(id);
        if (post.getLikes().stream().filter(e -> e.getUser().equals(userId)).count() >= 1) throw new IllegalArgumentException("Post already liked");
        LikeData like = new LikeData(userId);
        post.getLikes().add(like);
        postRepository.save(post);
        return post;
    }

    @Override
    public Post unlikePost(String id) {
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String userId = user.get_id();
        Post post = this.findById(id);
        Optional<LikeData> like = post.getLikes().stream().filter(e -> e.getUser().equals(userId)).reduce((a, b) -> null);
        if (!like.isPresent()) throw new IllegalArgumentException("Post not liked");
        post.getLikes().remove(like.get());
        postRepository.save(post);
        return post;
    }

    @Override
    public Post commentOnPost(String id, CreatePostDto dto) {
        if (dto.getText() == null) throw new IllegalArgumentException("Text is required");

        String text = dto.getText();
        User user = authService.getUserFromSecurityContext().orElseThrow(() -> new NoSuchElementException("User not found"));
        String userId = user.get_id();

        Post post = this.findById(id);
        CommentData commentData = new CommentData(text, userId);
        post.getComments().add(commentData);
        postRepository.save(post);
        return post;
    }

    @Override
    public Post removeCommentOnPost(String id, String commentId) {
        Post post = this.findById(id);
        Stream<CommentData> data = post.getComments().stream().filter(e -> e.getUser() == commentId);
        if (data.count() < 1) throw new NoSuchElementException("Comment not found");
        CommentData oldest = data.toList().get(0);
        post.getComments().remove(oldest);
        postRepository.save(post);
        return post;
    }
}
