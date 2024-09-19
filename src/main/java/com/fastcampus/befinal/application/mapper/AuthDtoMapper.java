package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.type.CertificationType;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.command.JwtCommand;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.domain.info.JwtInfo;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static com.fastcampus.befinal.common.contant.AuthConstant.*;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_AUTHORITY;
import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.INVALID_CERTIFICATION_TYPE;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {
    @Mapping(source = "certNoCheckToken", target = "certificationNumberCheckToken")
    AuthCommand.SignUpRequest toAuthCommand(AuthDto.SignUpRequest request);

    AuthCommand.CheckIdDuplicationRequest toAuthCommand(AuthDto.CheckIdDuplicationRequest request);

    SmsCommand.SendCertificationNumberRequest toAuthCommand(AuthDto.SendCertificationNumberRequest request);

    @Mapping(source = "certNo", target = "certificationNumber")
    AuthCommand.CheckCertificationNumberRequest toAuthCommand(AuthDto.CheckCertificationNumberRequest request);

    AuthCommand.SignInRequest toAuthCommand(AuthDto.SignInRequest request);

    @Mapping(source = "id", target = "userId")
    JwtCommand.CreateJwtRequest toJwtCommand(AuthDto.SignInRequest request);

    JwtCommand.ReissueJwtRequest toJwtCommand(AuthDto.ReissueJwtRequest request);

    AuthCommand.FindIdRequest toAuthCommand(AuthDto.FindIdRequest request);

    AuthCommand.FindPasswordRequest toAuthCommand(AuthDto.FindPasswordRequest request);

    AuthCommand.EditPasswordRequest toAuthCommand(AuthDto.EditPasswordRequest request);

    @Mapping(source = "token", target = "idCheckToken")
    AuthDto.CheckIdDuplicationResponse from(AuthInfo.CheckIdTokenInfo info);

    @Mapping(source = "token", target = "certNoCheckToken")
    AuthDto.CheckCertificationNumberResponse from(AuthInfo.CheckCertificationNumberTokenInfo info);

    @Mapping(target = "userInfo.authority", expression = "java(mapRoleToAuthorityName(userInfo))")
    AuthDto.SignInResponse of(AuthInfo.UserInfo userInfo, JwtInfo.TokenInfo tokenInfo);

    AuthDto.ReissueJwtResponse from(JwtInfo.TokenInfo info);

    AuthDto.FindIdResponse from(AuthInfo.FindIdInfo info);

    @Mapping(source = "token", target = "passwordResetToken")
    AuthDto.PasswordResetTokenResponse from(AuthInfo.PasswordResetTokenInfo info);

    default CertificationType mapStringToCertificationType(String type) {
        switch (type) {
            case "SignUp" -> {
                return CertificationType.SIGN_UP;
            }
            case "UpdateUser" -> {
                return CertificationType.UPDATE_USER;
            }
            case "FindId" -> {
                return CertificationType.FIND_ID;
            }
            case "FindPassword" -> {
                return CertificationType.FIND_PASSWORD;
            }
            default -> throw new BusinessException(INVALID_CERTIFICATION_TYPE);
        }
    }

    default String mapRoleToAuthorityName(AuthInfo.UserInfo info) {
        switch (info.role()) {
            case USER_AUTHORITY -> {
                return USER_AUTHORITY_NAME;
            }
            case ADMIN_AUTHORITY -> {
                return ADMIN_AUTHORITY_NAME;
            }
            default -> throw new BusinessException(INVALID_AUTHORITY);
        }
    }
}
