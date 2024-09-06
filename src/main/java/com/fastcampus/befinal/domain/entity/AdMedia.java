package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "AdMedia")
@Table(name = "ad_media")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, name = "name", columnDefinition = "varchar(50)")
    private String name;
}
