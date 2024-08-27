package com.fastcampus.befinal.domain.service;

import com.fastcampus.befinal.domain.command.SmsCommand;

public interface SmsService {
    void sendCertificationNumber(SmsCommand.SendCertificationNumberRequest command);
}
