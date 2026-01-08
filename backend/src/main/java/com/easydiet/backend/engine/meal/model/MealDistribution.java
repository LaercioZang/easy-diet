package com.easydiet.backend.engine.meal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDistribution {

    private int mealsPerDay;
    private List<MealTarget> meals;
}
