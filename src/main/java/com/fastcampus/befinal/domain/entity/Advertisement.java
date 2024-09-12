package com.fastcampus.befinal.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Builder
@Getter
@DynamicUpdate
@AllArgsConstructor
@Entity(name = "Advertisement")
@Table(name = "advertisement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Advertisement {
    @Id
    @Column(nullable = false, name = "id", columnDefinition = "varchar(50)")
    private String id;

    @Column(nullable = false, name = "product", columnDefinition = "varchar(50)")
    private String product;

    @Column(nullable = false, name = "advertiser", columnDefinition = "varchar(50)")
    private String advertiser;

    @Column(nullable = false, name = "state", columnDefinition = "tinyint")
    private Boolean state;

    @Column(nullable = false, name = "same", columnDefinition = "tinyint")
    private Boolean same;

    @Column(nullable = false, name = "issue", columnDefinition = "tinyint")
    private Boolean issue;

    @Column(nullable = false, name = "post_datetime", columnDefinition = "datetime")
    private LocalDateTime postDateTime;

    @Column(name = "assign_datetime", columnDefinition = "datetime")
    private LocalDateTime assignDateTime;

    @Column(name = "task_datetime", columnDefinition = "datetime")
    private LocalDateTime taskDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", columnDefinition = "bigint", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserSummary assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier_id", columnDefinition = "bigint", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private UserSummary modifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_category_id", columnDefinition = "bigint", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private AdCategory adCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id", columnDefinition = "bigint", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private AdCategory adMedia;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ad_decision_id", columnDefinition = "bigint", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private AdDecision adDecision;

    @OneToOne(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private AdContent adContent;

    public void updateAdDecision(AdDecision adDecision){
        this.adDecision = adDecision;
    }
    public void updateState(Boolean state){
        this.state = state;
    }
}
