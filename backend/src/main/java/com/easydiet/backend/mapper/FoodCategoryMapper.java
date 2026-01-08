package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.dto.FoodCategoryResponse;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;

public final class FoodCategoryMapper {

    private FoodCategoryMapper() {
    }

    public static FoodCategory toDomain(FoodCategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        return FoodCategory.builder()
            .id(entity.getId())
            .code(entity.getCode())
            .name(entity.getName())
            .active(entity.isActive())
            .build();
    }

    public static FoodCategoryResponse toResponse(FoodCategory domain) {
        if (domain == null) {
            return null;
        }

        return FoodCategoryResponse.builder()
            .id(domain.getId())
            .code(domain.getCode().name())
            .name(domain.getName())
            .active(domain.isActive())
            .build();
    }
}
