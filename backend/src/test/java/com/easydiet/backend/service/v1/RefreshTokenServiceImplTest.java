package com.easydiet.backend.service.v1;

import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.auth.RefreshTokenEntity;
import com.easydiet.backend.persistence.auth.RefreshTokenRepository;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import com.easydiet.backend.service.auth.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RefreshTokenServiceImplTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setup() {
        user = userRepository.save(
                UserEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Test User")
                        .email("test@easydiet.com")
                        .passwordHash("hash")
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @Test
    void shouldIssueAndRotateRefreshToken() {

        // issue first token
        RefreshTokenEntity first =
                refreshTokenService.issue(user);

        assertThat(first.isRevoked()).isFalse();

        // rotate
        UserEntity returnedUser =
                refreshTokenService.validateAndRotate(first.getToken());

        assertThat(returnedUser.getId()).isEqualTo(user.getId());

        RefreshTokenEntity reloaded =
                refreshTokenRepository.findById(first.getId()).orElseThrow();

        assertThat(reloaded.isRevoked()).isTrue();
    }

    @Test
    void shouldThrowWhenRefreshTokenIsInvalid() {

        assertThatThrownBy(() ->
                refreshTokenService.validateAndRotate("invalid-token")
        )
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> {
                    DomainException de = (DomainException) ex;
                    assertThat(de.getErrorCode())
                            .isEqualTo(ErrorCode.INVALID_ARGUMENT);
                });
    }

    @Test
    void shouldThrowWhenRefreshTokenIsExpired() {

        RefreshTokenEntity expired = refreshTokenRepository.save(
                RefreshTokenEntity.builder()
                        .token("expired-token")
                        .user(user)
                        .expiresAt(Instant.now().minusSeconds(60))
                        .revoked(false)
                        .build()
        );

        assertThatThrownBy(() ->
                refreshTokenService.validateAndRotate(expired.getToken())
        )
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> {
                    DomainException de = (DomainException) ex;
                    assertThat(de.getErrorCode())
                            .isEqualTo(ErrorCode.INVALID_ARGUMENT);
                });
    }

    @Test
    void shouldRevokeRefreshToken() {

        RefreshTokenEntity token =
                refreshTokenRepository.save(
                        RefreshTokenEntity.builder()
                                .token("token")
                                .user(user)
                                .expiresAt(Instant.now().plusSeconds(3600))
                                .revoked(false)
                                .build()
                );

        refreshTokenService.revoke(token.getToken());

        RefreshTokenEntity reloaded =
                refreshTokenRepository.findById(token.getId()).orElseThrow();

        assertThat(reloaded.isRevoked()).isTrue();
    }
}
