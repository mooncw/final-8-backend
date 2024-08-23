package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendCertificationNumber(SmsCommand.SendCertificationNumberRequest command) {

    }
}
