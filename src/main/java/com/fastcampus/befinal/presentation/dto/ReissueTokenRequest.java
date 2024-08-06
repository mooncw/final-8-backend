package com.fastcampus.befinal.presentation.dto;

public record ReissueTokenRequest(
    String accessToken,
    String refreshToken
) { }
