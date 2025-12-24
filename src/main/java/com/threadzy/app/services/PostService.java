package com.threadzy.app.services;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threadzy.app.models.Post;
import com.threadzy.app.models.UserEntity;
import com.threadzy.app.records.PostsRequest;
import com.threadzy.app.records.PostsResponse;
import com.threadzy.app.repositories.PostRepository;
import com.threadzy.app.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    
    public List<PostsResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        if(posts.isEmpty()) {
            return Collections.emptyList();
        }
        return posts
        .stream()
        .map(post -> new PostsResponse(post.getPostId(),post.getAuthor().getId(),post.getContent(),post.getContentType(),post.getCreatedAt()))
        .toList();
    }

    public List<PostsResponse> getPostsByAuthor(UUID authorId) {
        List<Post> posts = postRepository.findByAuthorId(authorId);
        return posts
            .stream()
            .map(post -> new PostsResponse(post.getPostId(),post.getAuthor().getId(),post.getContent(),post.getContentType(),post.getCreatedAt()))
            .toList();
    }

    @Transactional
    public Post createPost(PostsRequest post) {
        UUID authorId = post.authorId();

        UserEntity author = userRepository.findById(authorId)
            .orElseThrow(()-> new EntityNotFoundException("User with user id : " + authorId + " was not found."));
        
        Post returnedPost = new Post();
        returnedPost.setAuthor(author);
        returnedPost.setContent(post.content());
        returnedPost.setContentType(post.contentType());
        returnedPost.setCreatedAt(Instant.now());

        return postRepository.save(returnedPost);
    }
}
