package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.SmsCommand;

public interface SmsStore {
    void store(SmsCommand.SendCertificationNumberRequest command, Object content);
}
