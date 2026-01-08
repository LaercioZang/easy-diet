package com.easydiet.backend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // === VALIDATION ===
    INVALID_ARGUMENT,
    NULL_VALUE,
    OUT_OF_RANGE,
    UNAUTHORIZED,
    FORBIDDEN,

    // === NUTRITION ENGINE ===
    UNSUPPORTED_DIET,
    INVALID_GOAL,
    INVALID_CALORIES,

    // === MEAL ENGINE ===
    INVALID_MEALS_PER_DAY,

    // === ADJUSTMENT ENGINE ===
    INVALID_BASE_CALORIES,

    // === GENERIC ===
    RESOURCE_NOT_FOUND,
    INTERNAL_ERROR
}
