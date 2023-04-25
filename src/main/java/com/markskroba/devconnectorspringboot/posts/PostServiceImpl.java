package com.markskroba.devconnectorspringboot.posts;

import com.markskroba.devconnectorspringboot.posts.dto.CreatePostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
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
        if (!postRepository.existsById(id)) throw new NoSuchElementException("Post not found");
        postRepository.deleteById(id);
        // todo: user not authorized when deleting posts of other users
        return new ResponseMessage("Post deleted");
    }

    @Override
    public Post createPost(CreatePostDto dto) {
        Post post = new Post(dto.getText(), "Username");
        postRepository.save(post);
        return post;
    }

    @Override
    public Post likePost(String id) {
        String userId = "6056d8958ba84fc81f46cfe7";
        Post post = this.findById(id);
        if (post.getLikes().stream().filter(e -> e.getUser().equals(userId)).count() >= 1) throw new IllegalArgumentException("Post already liked");
        LikeData like = new LikeData(userId);
        post.getLikes().add(like);
        postRepository.save(post);
        return post;
    }

    @Override
    public Post unlikePost(String id) {
        String userId = "6056d8958ba84fc81f46cfe7";
        Post post = this.findById(id);
        Optional<LikeData> like = post.getLikes().stream().filter(e -> e.getUser().equals(userId)).reduce((a, b) -> null);
        if (!like.isPresent()) throw new IllegalArgumentException("Post not liked");
        post.getLikes().remove(like.get());
        postRepository.save(post);
        return post;
    }

    @Override
    public Post commentOnPost(String id, CreatePostDto dto) {
        String text = dto.getText();
        String userId = "6056d8958ba84fc81f46cfe7";

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
