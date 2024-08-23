package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsSender;

@DataProvider
public class SmsSenderImpl implements SmsSender {
    @Override
    public String send(SmsCommand.SendCertificationNumberRequest command) {
        return null;
    }
}
