package com.easydiet.backend.controller.v1.auth;

import com.easydiet.backend.dto.auth.LoginRequest;
import com.easydiet.backend.dto.auth.LoginResponse;
import com.easydiet.backend.dto.auth.LogoutRequest;
import com.easydiet.backend.dto.auth.RefreshRequest;
import com.easydiet.backend.persistence.auth.RefreshTokenEntity;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.service.auth.AuthService;
import com.easydiet.backend.service.auth.RefreshTokenService;
import com.easydiet.backend.config.JwtProperties;
import com.easydiet.backend.config.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerV1 {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody @Valid RefreshRequest request) {

        UserEntity user = refreshTokenService.validateAndRotate(
                request.refreshToken()
        );

        String newAccessToken = jwtTokenProvider.generateToken(user);
        RefreshTokenEntity newRefreshToken =
                refreshTokenService.issue(user);

        return new LoginResponse(
                newAccessToken,
                jwtProperties.getExpirationSeconds(),
                newRefreshToken.getToken()
        );
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody @Valid LogoutRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }
}
