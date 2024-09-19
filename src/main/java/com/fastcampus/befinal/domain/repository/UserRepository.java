package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByUserIdAndNameAndPhoneNumber(String userId, String name, String phoneNumber);
}
