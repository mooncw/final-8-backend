package com.fastcampus.befinal.application.service;

import com.fastcampus.befinal.common.contant.JwtConstant;
import com.fastcampus.befinal.common.response.error.exception.BaseException;
import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.domain.command.UserCommand;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenReader;
import com.fastcampus.befinal.domain.dataprovider.CheckTokenStore;
import com.fastcampus.befinal.domain.dataprovider.UserStore;
import com.fastcampus.befinal.domain.entity.User;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.UserInfo;
import com.fastcampus.befinal.domain.service.JwtAuthService;
import com.fastcampus.befinal.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final CheckTokenReader checkTokenReader;
    private final CheckTokenStore checkTokenStore;
    private final JwtAuthService jwtAuthService;
    private final UserStore userStore;

    @Override
    @Transactional
    public void updateUser(UserCommand.UserUpdateRequest command, String authorizationHeader){
        if(command.phoneNumber() != null) {
            AuthInfo.CheckTokenInfo certificationNumberCheckTokenInfo =
                AuthInfo.CheckTokenInfo.from(command.certificationNumberCheckToken());

            validateCheckToken(certificationNumberCheckTokenInfo);
        }

        UserInfo.UserUpdateInfo userUpdateInfo = UserInfo.UserUpdateInfo.from(command);
        userStore.update(userUpdateInfo);
        jwtAuthService.setAuthentication(subStringToken(authorizationHeader));
    }

    private String subStringToken(String header){
        return header.substring(JwtConstant.JWT_PREFIX.length()).trim();
    }

    private void validateCheckToken(AuthInfo.CheckTokenInfo tokenInfo){
        if(!checkTokenReader.exists(tokenInfo)) {
            throw new BusinessException(INVALID_CERTIFICATION_NUMBER_CHECK_TOKEN);
        }
        checkTokenStore.delete(tokenInfo);
    }

    @Override
    public void updatePassword(UserCommand.PasswordUpdateRequest command, String authorizationHeader){
        validPassword(command);

        UserInfo.PasswordUpdateInfo userInfo = UserInfo.PasswordUpdateInfo.from(command);

        jwtAuthService.setAuthentication(subStringToken(authorizationHeader));
    }

    private void validPassword(UserCommand.PasswordUpdateRequest command){
        if(!passwordEncoder.matches(command.password(), command.currentPassword())){
            throw new RuntimeException("비밀번호 오류");
        }
    }
}
