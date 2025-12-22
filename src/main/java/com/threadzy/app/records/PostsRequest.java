package com.threadzy.app.records;

import java.util.UUID;

import com.threadzy.app.enums.ContentType;

public record PostsRequest(String content,ContentType contentType,UUID authorId) {  
}
