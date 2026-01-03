package com.easydiet.backend.exception;

import java.util.Map;

public final class ErrorCatalog {

    private ErrorCatalog() {}

    private static final Map<ErrorCode, String> MESSAGES = Map.ofEntries(

        // VALIDATION
        Map.entry(ErrorCode.INVALID_ARGUMENT, "Invalid argument provided"),
        Map.entry(ErrorCode.NULL_VALUE, "Required value is null"),
        Map.entry(ErrorCode.OUT_OF_RANGE, "Value is out of allowed range"),

        // NUTRITION ENGINE
        Map.entry(ErrorCode.UNSUPPORTED_DIET, "Diet type is not supported"),
        Map.entry(ErrorCode.INVALID_GOAL, "Invalid goal"),
        Map.entry(ErrorCode.INVALID_CALORIES, "Calories must be greater than zero"),

        // MEAL ENGINE
        Map.entry(ErrorCode.INVALID_MEALS_PER_DAY, "Meals per day must be greater than zero"),

        // ADJUSTMENT ENGINE
        Map.entry(ErrorCode.INVALID_BASE_CALORIES, "Base calories must be greater than zero"),

        // GENERIC
        Map.entry(ErrorCode.INTERNAL_ERROR, "Unexpected internal error")
    );

    public static String message(ErrorCode code) {
        return MESSAGES.getOrDefault(
            code,
            "Unknown error"
        );
    }
}
