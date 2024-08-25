package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.info.SmsInfo;

public interface SmsStore {
    void store(SmsInfo.SmsCertificationInfo info);
}
