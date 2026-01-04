package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.dto.FoodResponse;
import com.easydiet.backend.persistence.food.FoodEntity;

public final class FoodMapper {

    private FoodMapper() {
    }

    public static Food toDomain(FoodEntity entity) {
        if (entity == null) {
            return null;
        }

        return Food.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(FoodCategoryMapper.toDomain(entity.getFoodCategory()))
                .calories(entity.getCalories())
                .protein(entity.getProtein())
                .carbs(entity.getCarbs())
                .fat(entity.getFat())
                .active(entity.isActive())
                .build();
    }

    public static FoodResponse toResponse(Food food) {
        if (food == null) {
            return null;
        }

        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .calories(food.getCalories())
                .protein(food.getProtein())
                .carbs(food.getCarbs())
                .fat(food.getFat())
                .category(
                        FoodCategoryMapper.toResponse(food.getCategory())
                )
                .build();
    }
}
