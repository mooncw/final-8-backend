package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdSimilarityPK implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_ad_id", columnDefinition = "varchar(50)", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Advertisement inspectionAdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comparison_ad_id", columnDefinition = "varchar(50)", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Advertisement comparisonAdId;
}
