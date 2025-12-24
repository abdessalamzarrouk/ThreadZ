package com.threadzy.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.threadzy.app.models.Post;
import com.threadzy.app.records.PostsRequest;
import com.threadzy.app.records.PostsResponse;
import com.threadzy.app.services.PostService;

@RestController
@CrossOrigin("http://localhost:5432")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostsResponse>> getAllPosts() {
        List<PostsResponse> returnedPosts = postService.getPosts();
        return ResponseEntity.ok(returnedPosts);   
    }

    @GetMapping("/posts/author/{authorId}")
    public ResponseEntity<List<PostsResponse>> getPostsByAuthor(@PathVariable UUID authorId) {
        List<PostsResponse> returnedPosts = postService.getPostsByAuthor(authorId);
        return ResponseEntity.ok(returnedPosts);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> uploadPost(@RequestBody PostsRequest post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post));
    }
}
