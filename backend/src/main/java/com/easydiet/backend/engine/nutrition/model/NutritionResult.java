package com.easydiet.backend.engine.nutrition.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NutritionResult {
    private int totalCalories;
    private double proteinGrams;
    private double carbsGrams;
    private double fatGrams;
}
