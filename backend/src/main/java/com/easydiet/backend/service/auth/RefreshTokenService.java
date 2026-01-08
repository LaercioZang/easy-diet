package com.easydiet.backend.service.auth;

import com.easydiet.backend.persistence.auth.RefreshTokenEntity;
import com.easydiet.backend.persistence.user.UserEntity;

public interface RefreshTokenService {
    RefreshTokenEntity issue(UserEntity user);
    UserEntity validateAndRotate(String refreshToken);
    void revoke(String token);
}
