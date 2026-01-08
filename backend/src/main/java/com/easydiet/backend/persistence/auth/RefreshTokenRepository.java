package com.easydiet.backend.persistence.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    @Modifying
    @Query("update RefreshTokenEntity r set r.revoked = true where r.user.id = :userId")
    void revokeAllByUserId(@Param("userId") UUID userId);

    Optional<RefreshTokenEntity> findByToken(String token);
}
