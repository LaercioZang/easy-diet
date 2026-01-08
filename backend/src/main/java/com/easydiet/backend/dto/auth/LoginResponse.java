package com.easydiet.backend.dto.auth;

public record LoginResponse(
        String accessToken,
        long expiresIn,
        String refreshToken
) {}
