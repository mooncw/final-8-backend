package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.dataprovider.AdDecisionReader;
import com.fastcampus.befinal.domain.entity.AdDecision;
import com.fastcampus.befinal.domain.entity.AdProvision;
import com.fastcampus.befinal.domain.entity.Advertisement;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class IssueAdInfo {
    @Builder
    public record IssueAdDetailInfo(
        String id,
        String product,
        String advertiser,
        String category,
        LocalDateTime postDateTime,
        String assigneeName,
        String modifierName,
        String content
    ){}

    @Builder
    public record IssueAdReviewInfo(
        Integer provisionArticle,
        String provisionContent,
        String sentence,
        String opinion
    ){}

    @Builder
    public record IssueAdDetailAllInfo(
        IssueAdDetailInfo issueAdDetail,
        List<IssueAdReviewInfo> issueAdReviewList
    ){
        public static IssueAdDetailAllInfo of(IssueAdDetailInfo issueAdDetail, List<IssueAdReviewInfo> issueAdReviewList){
            return IssueAdDetailAllInfo.builder()
                .issueAdDetail(issueAdDetail)
                .issueAdReviewList(issueAdReviewList)
                .build();
        }
    }

    @Builder
    public record IssueAdReviewSaveInfo(
        Advertisement advertisement,
        String sentence,
        String opinion,
        AdProvision adProvision
    ) {
        public static IssueAdReviewSaveInfo of(String sentence, String opinion, Advertisement advertisement, AdProvision adProvision) {
            return IssueAdReviewSaveInfo.builder()
                .sentence(sentence)
                .opinion(opinion)
                .adProvision(adProvision)
                .advertisement(advertisement)
                .build();
        }
    }

    @Builder
    public record IssueAdReviewUpdateInfo(
        Long reviewId,
        String sentence,
        String opinion,
        AdProvision adProvision

    ){
        public static IssueAdReviewUpdateInfo of(IssueAdDto.IssueAdReview command, AdProvision adProvision){
            return IssueAdReviewUpdateInfo.builder()
                .reviewId(command.reviewId())
                .sentence(command.sentence())
                .opinion(command.opinion())
                .adProvision(adProvision)
                .build();
        }
    }

    @Builder
    public record IssueAdReviewDeleteInfo(
        Long reviewId
    ){
        public static IssueAdReviewDeleteInfo from(Long reviewId){
            return IssueAdReviewDeleteInfo.builder()
                .reviewId(reviewId)
                .build();
        }
    }

    @Builder
    public record IssueAdDecisionSaveInfo(
        Advertisement advertisement,
        AdDecision adDecision
    ){
        public static IssueAdDecisionSaveInfo of(Advertisement advertisement, AdDecision adDecision){
            return IssueAdDecisionSaveInfo.builder()
                .advertisement(advertisement)
                .adDecision(adDecision)
                .build();
        }
    }

    @Builder
    public record IssueAdProvisionInfo(
        Integer id,
        Integer article,
        String content
    ){
        public static IssueAdProvisionInfo from(AdProvision adProvision){
            return IssueAdProvisionInfo.builder()
                .id(adProvision.getId())
                .article(adProvision.getArticle())
                .content(adProvision.getContent())
                .build();
        }
    }

    @Builder
    public record IssueAdProvisionListInfo(
        List<IssueAdProvisionInfo> provisionList
    ) {
        public static IssueAdProvisionListInfo from(List<IssueAdProvisionInfo> infoList){
            return IssueAdProvisionListInfo.builder()
                .provisionList(infoList)
                .build();
        }
    }

    @Builder
    public record IssueAdDecisionInfo(
        Long id,
        String decision
    ){
        public static IssueAdDecisionInfo from(AdDecision adDecision){
            return IssueAdDecisionInfo.builder()
                .id(adDecision.getId())
                .decision(adDecision.getDecision())
                .build();
        }
    }

    @Builder
    public record IssueAdDecisionListInfo(
        List<IssueAdDecisionInfo> decisionList
    ) {
        public static IssueAdDecisionListInfo from(List<IssueAdDecisionInfo> infoList){
            return IssueAdDecisionListInfo.builder()
                .decisionList(infoList)
                .build();
        }
    }
}
