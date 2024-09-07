package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "AdContent")
@Table(name = "ad_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdContent {
    @Id
    @Column(nullable = false, name = "id", columnDefinition = "varchar(50)")
    private String id;

    @Column(nullable = false, name = "content", columnDefinition = "text")
    private String content;

    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId
    @JoinColumn(name = "id", columnDefinition = "varchar(50)", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Advertisement advertisement;
}
