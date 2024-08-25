package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.command.SmsCommand;
import lombok.Builder;

import java.time.ZonedDateTime;

public class SmsInfo {
    @Builder
    public record SmsCertificationInfo(
        String phoneNumber,
        String certificationNumber,
        ZonedDateTime requestTime
    ) {
        public static SmsCertificationInfo of(SmsCommand.SendCertificationNumberRequest command,
                                              String certificationNumber, ZonedDateTime requestTime) {
            return SmsCertificationInfo.builder()
                .phoneNumber(command.phoneNumber())
                .certificationNumber(certificationNumber)
                .requestTime(requestTime)
                .build();
        }
    }
}
