package com.Project.kharchaTracker.repository;

import com.Project.kharchaTracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndUserIdNot(String email, String userId);
}
