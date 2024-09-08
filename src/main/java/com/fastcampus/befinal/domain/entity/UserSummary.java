package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
@Entity(name = "UserSummary")
@Table(name = "user_summary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name", columnDefinition = "varchar(10)")
    private String name;

    public void updateDeletedUserInfo() {
        this.name = "-";
    }
}
