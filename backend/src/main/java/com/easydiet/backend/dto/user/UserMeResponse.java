package com.easydiet.backend.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserMeResponse(
        UUID id,
        String name,
        String email,
        Instant createdAt
) {}
