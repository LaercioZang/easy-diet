package com.easydiet.backend.engine.meal.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MealTarget {

    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;
}
