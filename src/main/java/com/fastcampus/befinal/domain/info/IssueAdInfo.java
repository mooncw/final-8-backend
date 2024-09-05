package com.fastcampus.befinal.domain.info;

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
}
