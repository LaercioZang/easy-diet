package com.easydiet.backend.dto.meal;

import java.math.BigDecimal;
import java.util.UUID;

public record FoodItemResponse(
        UUID id,
        UUID foodId,
        String foodName,
        BigDecimal quantity,
        BigDecimal calories,
        BigDecimal protein,
        BigDecimal carbs,
        BigDecimal fat
) {
}
