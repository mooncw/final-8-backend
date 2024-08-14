package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.info.AuthInfo;
import com.fastcampus.befinal.presentation.dto.AuthDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {
    AuthCommand.SignInRequest toSignInCommand(AuthDto.SignInRequest request);

    AuthCommand.CreateJwtRequest toCreateJwtCommand (AuthDto.SignInRequest request);

    AuthCommand.ReissueJwtRequest toReissueJwtCommand(AuthDto.ReissueJwtRequest request);

    AuthDto.ReissueJwtResponse from(AuthInfo.JwtInfo jwtInfo);
}
