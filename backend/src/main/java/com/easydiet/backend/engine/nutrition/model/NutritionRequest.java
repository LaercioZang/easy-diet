package com.easydiet.backend.engine.nutrition.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NutritionRequest {
    private int totalCalories;
}
