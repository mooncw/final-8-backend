package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsCertificationStore;
import com.fastcampus.befinal.domain.dataprovider.SmsSender;
import com.fastcampus.befinal.domain.dataprovider.UserUnionViewReader;
import com.fastcampus.befinal.domain.info.SmsInfo;
import com.fastcampus.befinal.domain.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.PHONE_NUMBER_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {
    private final SmsSender smsSender;
    private final SmsCertificationStore smsCertificationStore;
    private final UserUnionViewReader userUnionViewReader;

    @Override
    @Transactional(readOnly = true)
    public void sendCertificationNumber(SmsCommand.SendCertificationNumberRequest command) {
        ZonedDateTime requestTime = ZonedDateTime.now();

        validateUserPhoneNumber(command);

        String certificationNumber = smsSender.send(command);

        SmsInfo.SmsCertificationInfo smsCertificationInfo = SmsInfo.SmsCertificationInfo.of(command, certificationNumber, requestTime);

        smsCertificationStore.store(smsCertificationInfo);
    }

    private void validateUserPhoneNumber(SmsCommand.SendCertificationNumberRequest command) {
        if (userUnionViewReader.existsUserPhoneNumber(command)) {
            throw new BusinessException(PHONE_NUMBER_ALREADY_EXIST);
        }
    }
}
