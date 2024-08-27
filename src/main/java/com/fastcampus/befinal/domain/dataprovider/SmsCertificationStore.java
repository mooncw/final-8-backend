package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.info.SmsInfo;

public interface SmsCertificationStore {
    void store(SmsInfo.SmsCertificationInfo info);
}
