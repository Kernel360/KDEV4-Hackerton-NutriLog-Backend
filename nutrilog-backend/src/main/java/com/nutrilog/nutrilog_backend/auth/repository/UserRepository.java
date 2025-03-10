package com.nutrilog.nutrilog_backend.auth.repository;

import com.nutrilog.nutrilog_backend.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialKey(String socialKey);
}
