package com.easydiet.backend.service;

import com.easydiet.backend.persistence.meal.FoodItemEntity;

import java.util.List;
import java.util.UUID;

public interface FoodItemService {

    FoodItemEntity create(
            UUID userId,
            UUID mealId,
            UUID foodId,
            double quantity
    );

    List<FoodItemEntity> findByMeal(
            UUID userId,
            UUID mealId
    );

    void delete(
            UUID userId,
            UUID foodItemId
    );
}
