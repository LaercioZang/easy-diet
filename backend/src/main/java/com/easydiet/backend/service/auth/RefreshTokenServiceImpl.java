package com.easydiet.backend.service.auth;

import com.easydiet.backend.config.security.RefreshTokenProperties;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.auth.RefreshTokenEntity;
import com.easydiet.backend.persistence.auth.RefreshTokenRepository;
import com.easydiet.backend.persistence.user.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final RefreshTokenProperties props;

    @Override
    public RefreshTokenEntity issue(UserEntity user) {

        // revoga tokens antigos (opcional, mas recomendado)
        repository.revokeAllByUserId(user.getId());

        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(Instant.now().plus(props.getExpirationDays(), ChronoUnit.DAYS))
                .revoked(false)
                .build();

        return repository.save(entity);
    }

    @Override
    public UserEntity validateAndRotate(String refreshToken) {

        RefreshTokenEntity entity = repository.findByTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.INVALID_ARGUMENT
                ));

        if (entity.getExpiresAt().isBefore(Instant.now())) {
            entity.setRevoked(true);
            throw new DomainException(
                    ErrorCode.INVALID_ARGUMENT
            );
        }

        // Rotação
        entity.setRevoked(true);

        return entity.getUser();
    }

    @Override
    @Transactional
    public void revoke(String token) {

        RefreshTokenEntity refreshToken =
                repository.findByToken(token)
                        .orElseThrow(() -> new DomainException(
                                ErrorCode.INVALID_ARGUMENT
                        ));

        refreshToken.setRevoked(true);
    }
}
