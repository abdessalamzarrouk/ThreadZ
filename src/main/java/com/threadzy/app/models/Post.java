package com.threadzy.app.models;

import java.util.UUID;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.threadzy.app.enums.ContentType;
import lombok.Data;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID postId;
    
    private String title;
    private String content; // for now content will be text and later will implement videos , photos ....
    private ContentType contentType;
    private long voteCount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private UserEntity author;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
