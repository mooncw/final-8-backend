package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity(name = "UserManagement")
@Table(name = "user_management")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserManagement {
    @Id
    @Column(nullable = false, unique = true, name = "id", columnDefinition = "varchar(15)")
    private String id;

    @Column(nullable = false, name = "name", columnDefinition = "varchar(10)")
    private String name;

    @Column(nullable = false, name = "password", columnDefinition = "varchar(50)")
    private String password;

    @Column(nullable = false, unique = true, name = "phone_number", columnDefinition = "varchar(11)")
    private String phoneNumber;

    @Column(nullable = false, unique = true, name = "emp_number", columnDefinition = "varchar(50)")
    private String empNumber;

    @Column(nullable = false, unique = true, name = "email", columnDefinition = "varchar(30)")
    private String email;

    @Column(nullable = false, name = "signup_datetime", columnDefinition = "datetime")
    private LocalDateTime signUpDateTime;

}
