package com.easydiet.backend.domain.user;

import com.easydiet.backend.domain.user.enums.Gender;

import java.math.BigDecimal;

public record BodyProfile(
        BigDecimal weightKg,
        int heightCm,
        int age,
        Gender gender
) {}
