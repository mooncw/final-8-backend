package com.fastcampus.befinal.domain.command;

import com.fastcampus.befinal.common.type.CertificationType;
import lombok.Builder;

public class SmsCommand {
    @Builder
    public record SendCertificationNumberRequest(
        CertificationType type,
        String phoneNumber
    ) {}
}
