package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.common.response.error.exception.BusinessException;
import com.fastcampus.befinal.common.type.CertificationType;
import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.command.JwtCommand;
import com.fastcampus.befinal.domain.command.SmsCommand;
import com.fastcampus.befinal.domain.info.JwtInfo;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import org.mapstruct.*;

import java.util.Objects;

import static com.fastcampus.befinal.common.response.error.info.AuthErrorCode.NOT_VALID_CERTIFICATION_TYPE;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {
    AuthCommand.SignUpRequest toAuthCommand(AuthDto.SignUpRequest request);

    AuthCommand.CheckIdDuplicationRequest toAuthCommand(AuthDto.CheckIdDuplicationRequest request);

    SmsCommand.SendCertificationNumberRequest toAuthCommand(AuthDto.SendCertificationNumberRequest request);

    AuthCommand.UpdateCheckListRequest toAuthCommand(String type, String phoneNumber);

    @Mapping(source = "certNo", target = "certificationNumber")
    AuthCommand.CheckCertificationNumberRequest toAuthCommand(AuthDto.CheckCertificationNumberRequest request);

    AuthCommand.SignInRequest toAuthCommand(AuthDto.SignInRequest request);

    JwtCommand.CreateJwtRequest toJwtCommand (AuthDto.SignInRequest request);

    JwtCommand.ReissueJwtRequest toJwtCommand(AuthDto.ReissueJwtRequest request);

    AuthDto.ReissueJwtResponse from(JwtInfo.TokenInfo info);

    default CertificationType mapStringToCertificationType(String type) {
        switch (type) {
            case "SignUp" -> {
                return CertificationType.SIGN_UP;
            }
            default -> throw new BusinessException(NOT_VALID_CERTIFICATION_TYPE);
        }
    }
}
