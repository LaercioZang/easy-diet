package com.easydiet.backend.dto.meal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record FoodItemCreateRequest(

        @NotNull
        UUID foodId,

        @NotNull
        @Positive
        BigDecimal quantity
) {
}
