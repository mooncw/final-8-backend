package com.fastcampus.befinal.infrastructure.redis.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.SmsCertificationReader;
import com.fastcampus.befinal.domain.entity.SmsCertification;

@DataProvider
public class SmsCertificationReaderImpl implements SmsCertificationReader {
    @Override
    public SmsCertification find(AuthCommand.CheckCertificationNumberRequest command) {
        return null;
    }
}
