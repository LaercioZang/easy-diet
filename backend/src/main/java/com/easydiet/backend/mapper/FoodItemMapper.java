package com.easydiet.backend.mapper;

import com.easydiet.backend.dto.meal.FoodItemResponse;
import com.easydiet.backend.persistence.meal.FoodItemEntity;

public final class FoodItemMapper {

    private FoodItemMapper() {
    }

    public static FoodItemResponse toResponse(FoodItemEntity entity) {
        if (entity == null) {
            return null;
        }

        return new FoodItemResponse(
                entity.getId(),
                entity.getFood().getId(),
                entity.getFood().getName(),
                entity.getQuantity(),
                entity.getCalories(),
                entity.getProtein(),
                entity.getCarbs(),
                entity.getFat()
        );
    }
}
