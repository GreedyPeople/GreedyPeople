package com.sparta.greeypeople.user.repository;

import com.sparta.greeypeople.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long kakaoId);
}