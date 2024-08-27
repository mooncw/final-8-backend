package com.fastcampus.befinal.infrastructure.coolsms.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsSender;
import com.fastcampus.befinal.infrastructure.coolsms.template.CoolsmsTemplate;
import lombok.RequiredArgsConstructor;

import static com.fastcampus.befinal.common.contant.SmsConstant.CERTIFICATION_NUMBER_LENGTH;
import static com.fastcampus.befinal.common.contant.SmsConstant.CERTIFICATION_SENDER_NAME;

@DataProvider
@RequiredArgsConstructor
public class SmsSenderImpl implements SmsSender {
    private final CoolsmsTemplate coolsmsTemplate;

    @Override
    public String send(SmsCommand.SendCertificationNumberRequest command) {
        String certificationNumber = Generator.generateOnlyNumeric(CERTIFICATION_NUMBER_LENGTH);
        coolsmsTemplate.sendSms(command.phoneNumber(), createCertificationMessage(certificationNumber));
        return certificationNumber;
    }

    private String createCertificationMessage(String certificationNumber) {
        return "[" + CERTIFICATION_SENDER_NAME + "] 본인확인 인증번호 [" + certificationNumber + "]를 입력해주세요.";
    }
}
