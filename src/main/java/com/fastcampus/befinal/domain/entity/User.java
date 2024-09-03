package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(nullable = false, unique = true, name = "id", columnDefinition = "varchar(15)")
    private String id;

    @Column(nullable = false, name = "name", columnDefinition = "varchar(10)")
    private String name;

    @Setter
    @Column(nullable = false, name = "password", columnDefinition = "varchar(50)")
    private String password;

    @Setter
    @Column(nullable = false, unique = true, name = "phone_number", columnDefinition = "varchar(11)")
    private String phoneNumber;

    @Column(nullable = false, unique = true, name = "emp_number", columnDefinition = "varchar(50)")
    private String empNumber;

    @Setter
    @Column(nullable = false, unique = true, name = "email", columnDefinition = "varchar(30)")
    private String email;

    @Column(nullable = false, name = "signup_datetime", columnDefinition = "datetime")
    private LocalDateTime signUpDateTime;

    @Column(nullable = false, name = "final_login_datetime", columnDefinition = "datetime")
    private LocalDateTime finalLoginDateTime;

    @Column(nullable = false, name = "role", columnDefinition = "varchar(20)")
    private String role;

    public void updateFinalLoginDateTime() {
        this.finalLoginDateTime = LocalDateTime.now();
    }
}
