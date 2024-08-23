package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsStore;

@DataProvider
public class SmsStoreImpl implements SmsStore {
    @Override
    public void store(SmsCommand.SendCertificationNumberRequest command, Object content) {

    }
}
