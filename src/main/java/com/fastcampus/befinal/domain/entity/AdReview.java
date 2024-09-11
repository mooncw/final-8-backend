package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Builder
@DynamicUpdate
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id", columnDefinition = "varchar(50)", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Advertisement advertisement;

    public void updateSentenceAndOpinion(String sentence, String opinion){
        this.sentence = sentence;
        this.opinion = opinion;
    }
}
