package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
