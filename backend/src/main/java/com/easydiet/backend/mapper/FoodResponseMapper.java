package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.dto.FoodCategoryResponse;
import com.easydiet.backend.dto.FoodResponse;

public final class FoodResponseMapper {

    private FoodResponseMapper() {
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
                FoodCategoryResponse.builder()
                    .id(food.getCategory().getId())
                    .code(food.getCategory().getCode().toString())
                    .name(food.getCategory().getName())
                    .build()
            )
            .build();
    }
}
