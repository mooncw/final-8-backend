package com.fastcampus.befinal.domain.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName(value = "RefreshToken")
public class RefreshToken implements JwtRedisValue {
    private String token;
}
