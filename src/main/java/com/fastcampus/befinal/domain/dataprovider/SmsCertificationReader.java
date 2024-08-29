package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.entity.SmsCertification;

public interface SmsCertificationReader {
    SmsCertification find(AuthCommand.CheckCertificationNumberRequest command);
}
