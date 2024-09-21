package com.fastcampus.befinal.domain.entity;

import com.querydsl.core.annotations.QueryInit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity(name = "AdSimilarity")
@Table(name = "ad_similarity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdSimilarity {
    @EmbeddedId
    @QueryInit({"inspectionAd", "comparisonAd.adCategory"})
    private AdSimilarityPK adSimilarityPK;

    @Column(nullable = false, name = "similarity", precision = 5, scale = 4)
    private BigDecimal similarity;

    @Column(nullable = false, name = "same_sentence", columnDefinition = "text")
    private String sameSentence;

    @Column(nullable = false, name = "same_sentence_count", columnDefinition = "smallint")
    private Integer sameSentenceCount;
}
