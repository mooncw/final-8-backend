package com.fastcampus.befinal.domain.info;

import com.fastcampus.befinal.domain.command.SmsCommand;
import lombok.Builder;

public class SmsInfo {
    @Builder
    public record SmsCertificationInfo(
        String phoneNumber,
        String certificationNumber
    ) {
        public static SmsCertificationInfo of(SmsCommand.SendCertificationNumberRequest command, String certificationNumber) {
            return SmsCertificationInfo.builder()
                .phoneNumber(command.phoneNumber())
                .certificationNumber(certificationNumber)
                .build();
        }
    }
}
