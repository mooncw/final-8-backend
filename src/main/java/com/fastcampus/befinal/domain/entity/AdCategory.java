package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "AdCategory")
@Table(name = "ad_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, name = "category", columnDefinition = "varchar(50)")
    private String category;
}
