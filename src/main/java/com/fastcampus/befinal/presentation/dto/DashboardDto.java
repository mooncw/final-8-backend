package com.fastcampus.befinal.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardDto {
    @Builder
    public record DashboardDataResponse(
            AdCount adCount,
            List<DailyDone> dailyDoneList,
            List<RecentDone> recentDoneList
    ) {}

    @Builder
    public record AdCount(
            Integer totalAd,
            Integer myAd,
            Integer totalDoneAd,
            Integer myDoneAd,
            Integer totalNotDoneAd,
            Integer myNotDoneAd
    ) {}

    @Builder
    public record DailyDone(
            LocalDate date,
            Integer dailyMyDoneAd
    ) {}

    @Builder
    public record RecentDone(
            String adId,
            String adName,
            LocalDateTime adTaskDateTime
    ) {
        @JsonProperty("adTaskDateTime")
        public String getAdTaskDateTimeAsString() {
            return adTaskDateTime
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }
}
