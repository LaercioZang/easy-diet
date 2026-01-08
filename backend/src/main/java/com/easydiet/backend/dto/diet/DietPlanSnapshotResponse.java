package com.easydiet.backend.dto.diet;

import java.time.Instant;
import java.util.UUID;

public record DietPlanSnapshotResponse(
        UUID id,
        Integer tdee,
        String weekDistributionJson,
        Instant createdAt
) {}
