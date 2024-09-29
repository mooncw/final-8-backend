package com.fastcampus.befinal.common.util;

import lombok.Builder;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@ToString
public class PeriodUtil {
    private String stringYear;
    private String stringMonth;
    private String orderNumber;
    private Integer integerYear;
    private Integer integerMonth;
    private Character startGroup;
    private Character endGroup;

    public static PeriodUtil from(String period) {
        if (period == null) {
            LocalDate now = LocalDate.now();
            String term = now.getDayOfMonth() < 16 ? "1" : "2";
            period = now.getYear() + "-" + now.getMonthValue() + "-" + term;
        }

        String[] periodParts = period.split("-");

        String stringYear = periodParts[0];
        String stringMonth = periodParts[1];
        String orderNumber = periodParts[2];

        Integer integerYear = Integer.parseInt(stringYear);
        Integer integerMonth = Integer.parseInt(stringMonth);

        char startGroup = orderNumber.equals("1") ? 'A' : 'N';
        char endGroup = orderNumber.equals("1") ? 'M' : 'Z';

        return PeriodUtil.builder()
            .stringYear(stringYear)
            .stringMonth(stringMonth)
            .orderNumber(orderNumber)
            .integerYear(integerYear)
            .integerMonth(integerMonth)
            .startGroup(startGroup)
            .endGroup(endGroup)
            .build();
    }

    public LocalDateTime getStartAssignDateTime() {
        return this.orderNumber.equals("1") ?
            LocalDateTime.of(this.integerYear, this.integerMonth, 1, 0, 0, 0) :
            LocalDateTime.of(this.integerYear, this.integerMonth, 16, 0, 0, 0);
    }

    public LocalDateTime getEndAssignDateTime() {
        return this.orderNumber.equals("1") ?
            LocalDateTime.of(this.integerYear, this.integerMonth, 15, 23, 59, 59) :
            LocalDateTime.of(this.integerYear, this.integerMonth, LocalDate.of(this.integerYear, this.integerMonth, 1).lengthOfMonth(), 23, 59, 59);
    }
}
