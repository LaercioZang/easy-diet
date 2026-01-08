package com.easydiet.backend.engine.meal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealTarget {

    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;
}
