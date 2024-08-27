package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.SmsCommand;

public interface SmsSender {
    String send(SmsCommand.SendCertificationNumberRequest command);
}
