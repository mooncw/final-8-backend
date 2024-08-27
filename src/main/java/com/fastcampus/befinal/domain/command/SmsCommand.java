package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class SmsCommand {
    @Builder
    public record SendCertificationNumberRequest(
        String phoneNumber
    ) {}
}
