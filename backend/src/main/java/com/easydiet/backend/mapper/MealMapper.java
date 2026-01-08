package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.meal.command.MealCreateCommand;
import com.easydiet.backend.domain.meal.command.MealUpdateCommand;
import com.easydiet.backend.dto.meal.MealCreateRequest;
import com.easydiet.backend.dto.meal.MealResponse;
import com.easydiet.backend.dto.meal.MealUpdateRequest;
import com.easydiet.backend.persistence.meal.MealEntity;

public final class MealMapper {

    private MealMapper() {
    }

    public static MealCreateCommand toCreateCommand(MealCreateRequest request) {
        return MealCreateCommand.builder()
                .dayOfWeek(request.dayOfWeek())
                .name(request.name())
                .mealOrder(request.mealOrder())
                .build();
    }

    public static MealUpdateCommand toUpdateCommand(MealUpdateRequest request) {
        return MealUpdateCommand.builder()
                .name(request.name())
                .mealOrder(request.mealOrder())
                .build();
    }

    public static MealResponse toResponse(MealEntity entity) {
        if (entity == null) {
            return null;
        }

        return new MealResponse(
                entity.getId(),
                entity.getDayOfWeek(),
                entity.getName(),
                entity.getMealOrder(),
                entity.getTotalCalories(),
                entity.getTotalProtein(),
                entity.getTotalCarbs(),
                entity.getTotalFat()
        );
    }
}
