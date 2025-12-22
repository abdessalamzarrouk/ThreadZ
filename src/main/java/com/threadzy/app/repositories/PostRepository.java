package com.threadzy.app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threadzy.app.models.Post;

public interface PostRepository extends JpaRepository<Post,UUID> {
}
