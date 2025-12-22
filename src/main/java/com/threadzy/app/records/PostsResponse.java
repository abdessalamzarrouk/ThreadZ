package com.threadzy.app.records;

import java.time.Instant;
import java.util.UUID;

import com.threadzy.app.enums.ContentType;

public record PostsResponse(UUID postId, UUID authorId, String content, ContentType contentType, Instant createdAt) {
}
