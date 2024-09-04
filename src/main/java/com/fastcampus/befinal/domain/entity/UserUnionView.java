package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Getter
@Immutable
@Entity(name = "UserUnionView")
@Table(name = "user_union_view")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUnionView {
    @Id
    private String userId;

    private String phoneNumber;

    private String empNumber;

    private String email;
}
