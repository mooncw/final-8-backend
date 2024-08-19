package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "AdProvision")
@Table(name = "ad_provision")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdProvision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, name = "article", columnDefinition = "smallint")
    private Integer article;

    @Column(nullable = false, name = "content", columnDefinition = "varchar(50)")
    private String content;
}
