package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.food.Food;
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
}
