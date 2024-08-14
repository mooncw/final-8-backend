package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity(name = "User")
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
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

    @Column(nullable = false, name = "final_login_datetime", columnDefinition = "datetime")
    private LocalDateTime finalLoginDateTime;

    @Column(nullable = false, name = "role", columnDefinition = "varchar(20)")
    private String role;

    @Builder
    public User(String id, String name, String password, String phoneNumber, String empNumber, String email, LocalDateTime signUpDateTime, LocalDateTime finalLoginDateTime, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.empNumber = empNumber;
        this.email = email;
        this.signUpDateTime = signUpDateTime;
        this.finalLoginDateTime = finalLoginDateTime;
        this.role = "USER";
    }
}
