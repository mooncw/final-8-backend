package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.util.Generator;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsSender;
import com.fastcampus.befinal.domain.dataprovider.SmsStore;
import com.fastcampus.befinal.domain.info.SmsInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fastcampus.befinal.common.contant.SmsConstant.CERTIFICATION_NUMBER_LENGTH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("SmsService 테스트")
@ExtendWith(MockitoExtension.class)
class SmsServiceImplTest {
    @InjectMocks
    private SmsServiceImpl smsService;

    @Mock
    private SmsSender smsSender;

    @Mock
    private SmsStore smsStore;

    @Test
    @DisplayName("인증번호 요청 성공 테스트")
    void sendCertificationNumberTest() {
        //given
        SmsCommand.SendCertificationNumberRequest command = SmsCommand.SendCertificationNumberRequest.builder()
            .phoneNumber("01011112222")
            .build();

        String certificationNumber = Generator.generateOnlyNumeric(CERTIFICATION_NUMBER_LENGTH);

        doReturn(certificationNumber)
            .when(smsSender)
            .send(any(SmsCommand.SendCertificationNumberRequest.class));

        doNothing()
            .when(smsStore)
                .store(any(SmsInfo.SmsCertificationInfo.class));

        //when
        smsService.sendCertificationNumber(command);

        //verify
        verify(smsSender, times(1)).send(any(SmsCommand.SendCertificationNumberRequest.class));
        verify(smsStore, times(1)).store(any(SmsInfo.SmsCertificationInfo.class));
    }
}