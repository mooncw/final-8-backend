package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsSender;
import com.fastcampus.befinal.domain.dataprovider.SmsStore;
import com.fastcampus.befinal.domain.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    private final SmsSender smsSender;
    private final SmsStore smsStore;

    @Override
    public void sendCertificationNumber(SmsCommand.SendCertificationNumberRequest command) {
        String certificationNumber = smsSender.send(command);
        smsStore.store(command, certificationNumber);
    }
}
