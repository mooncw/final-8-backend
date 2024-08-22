package com.fastcampus.befinal.infrastructure.mysql.dataprovider;

import com.fastcampus.befinal.common.annotation.DataProvider;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.dataprovider.UserUnionViewReader;
import com.fastcampus.befinal.domain.repository.UserUnionViewRepository;
import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class UserUnionViewReaderImpl implements UserUnionViewReader {
    private final UserUnionViewRepository userUnionViewRepository;

    @Override
    public boolean exists(AuthCommand.SignUpRequest command) {
        return userUnionViewRepository.existsByIdOrPhoneNumberOrEmpNumberOrEmail(command.id(), command.phoneNumber(),
            command.empNo(), command.email());
    }
}
