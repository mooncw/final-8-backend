package com.fastcampus.befinal.domain.repository;

import com.fastcampus.befinal.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updatePasswordById(@Param("id") String id, @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.email = :email, u.phoneNumber = :phoneNumber WHERE u.id = :id")
    void updateUserInfoById(@Param("id") String id, @Param("email") String email, @Param("phoneNumber") String phoneNumber);
}
