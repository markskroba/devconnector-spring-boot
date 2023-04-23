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
    public Optional<Post> findById(String id) {
        return postRepository.findById(id);
    }

    @Override
    public Post deleteOneById(Post post) {
        postRepository.deleteById(post.get_id());
        return post;
    }

    @Override
    public Post createPost(CreatePostDto dto) {
        Post post = new Post(dto.getText(), "Username");
        postRepository.save(post);
        return post;
    }

    @Override
    public Post likePost(Post post) {
        String userId = "6056d8958ba84fc81f46cfe7";
        if (post.getLikes().stream().filter(e -> e.getUser().equals(userId)).count() >= 1) return null;
        LikeData like = new LikeData(userId);
        post.getLikes().add(like);
        postRepository.save(post);
        return post;
    }

    @Override
    public Post unlikePost(Post post) {
        String userId = "6056d8958ba84fc81f46cfe7";
        Optional<LikeData> like = post.getLikes().stream().filter(e -> e.getUser().equals(userId)).reduce((a, b) -> null);
        if (!like.isPresent()) return null;
        post.getLikes().remove(like.get());
        postRepository.save(post);
        return post;
    }

    @Override
    public Post commentOnPost(String id, CreatePostDto dto) {
        String text = dto.getText();
        String userId = "6056d8958ba84fc81f46cfe7";

        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) return null;
        Post postData =  post.get();
        CommentData comment = new CommentData(text, userId);
        postData.getComments().add(comment);
        postRepository.save(postData);
        return postData;
    }

    @Override
    public Post removeCommentOnPost(String id, String commentId) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) return null;
        Post post = optionalPost.get();
        Stream<CommentData> data = post.getComments().stream().filter(e -> e.getUser() == commentId);
        if (data.count() < 1) return null;
        CommentData oldest = data.toList().get(0);
        post.getComments().remove(oldest);
        postRepository.save(post);
        return post;
    }
}
