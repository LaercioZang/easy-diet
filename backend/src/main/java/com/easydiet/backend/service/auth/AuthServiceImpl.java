package com.easydiet.backend.service.auth;

import com.easydiet.backend.config.JwtProperties;
import com.easydiet.backend.config.JwtTokenProvider;
import com.easydiet.backend.dto.auth.LoginRequest;
import com.easydiet.backend.dto.auth.LoginResponse;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.auth.RefreshTokenEntity;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new DomainException(
                        ErrorCode.INVALID_ARGUMENT
                ));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new DomainException(
                    ErrorCode.INVALID_ARGUMENT
            );
        }

        // Access token (JWT)
        String accessToken = jwtTokenProvider.generateToken(user);

        // Refresh token (persistido)
        RefreshTokenEntity refreshToken = refreshTokenService.issue(user);

        return new LoginResponse(
                accessToken,
                jwtProperties.getExpirationSeconds(),
                refreshToken.getToken()
        );
    }
}
