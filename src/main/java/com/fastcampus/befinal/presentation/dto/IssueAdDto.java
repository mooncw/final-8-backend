package com.fastcampus.befinal.presentation.dto;

import com.fastcampus.befinal.domain.info.IssueAdInfo;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class IssueAdDto {
    @Builder
    public record IssueAdDetailResponse(
        String id,
        String product,
        String advertiser,
        String category,
        LocalDate postDate,
        String assigneeName,
        String modifierName,
        String content,
        List<IssueAdInfo.IssueAdReviewInfo> reviewList
    ){}
}
