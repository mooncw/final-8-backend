package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenReader;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN;
import static com.fastcampus.befinal.common.response.error.info.UserErrorCode.INVALID_CURRENT_PASSWORD;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final CheckTokenReader checkTokenReader;
    private final CheckTokenStore checkTokenStore;
    private final UserStore userStore;

    @Override
    @Transactional
    public void updateUser(UserCommand.UserUpdateRequest command){
        if(command.phoneNumber() != null) {
            AuthInfo.CheckTokenInfo certificationNumberCheckTokenInfo =
                AuthInfo.CheckTokenInfo.from(command.certNoCheckToken());

            validateCheckToken(certificationNumberCheckTokenInfo);
        }

        UserInfo.UserUpdateInfo userUpdateInfo = UserInfo.UserUpdateInfo.from(command);
        userStore.update(userUpdateInfo);
    }

    private void validateCheckToken(AuthInfo.CheckTokenInfo tokenInfo){
        if(!checkTokenReader.exists(tokenInfo)) {
            throw new BusinessException(INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN);
        }
        checkTokenStore.delete(tokenInfo);
    }

    @Override
    @Transactional
    public void updatePassword(UserCommand.PasswordUpdateRequest command){
        validPassword(command);

        UserInfo.PasswordUpdateInfo userInfo = UserInfo.PasswordUpdateInfo.from(command);
        userStore.update(userInfo);
    }

    private void validPassword(UserCommand.PasswordUpdateRequest command){
        if(!passwordEncoder.matches(command.currentPassword(), command.password())){
            throw new BusinessException(INVALID_CURRENT_PASSWORD);
        }
    }
}
