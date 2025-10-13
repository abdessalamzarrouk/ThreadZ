package com.threadzy.app.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threadzy.app.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,UUID> {
    Optional<UserEntity> findByUsername(String username);
}
