package com.fastcampus.befinal.domain.dataprovider;

import com.fastcampus.befinal.domain.command.AuthCommand;

public interface UserUnionViewReader {
    boolean exists(AuthCommand.SignUpRequest command);
}
