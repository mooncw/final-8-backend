package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "AdReview")
@Table(name = "ad_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "sentence", columnDefinition = "varchar(500)")
    private String sentence;

    @Column(nullable = false, name = "opinion", columnDefinition = "text")
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_provision_id", columnDefinition = "bigint", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private AdProvision adProvision;
}
