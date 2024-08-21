package com.fastcampus.befinal.application.mapper;

import com.fastcampus.befinal.domain.command.AuthCommand;
import com.fastcampus.befinal.domain.command.JwtCommand;
import com.fastcampus.befinal.domain.info.JwtInfo;
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
    AuthCommand.SignUpRequest toAuthCommand(AuthDto.SignUpRequest request);

    AuthCommand.SignInRequest toAuthCommand(AuthDto.SignInRequest request);

    JwtCommand.CreateJwtRequest toJwtCommand (AuthDto.SignInRequest request);

    JwtCommand.ReissueJwtRequest toJwtCommand(AuthDto.ReissueJwtRequest request);

    AuthDto.ReissueJwtResponse from(JwtInfo.TokenInfo info);
}
