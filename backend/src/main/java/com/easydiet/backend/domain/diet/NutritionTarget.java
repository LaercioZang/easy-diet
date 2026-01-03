package com.easydiet.backend.domain.diet;

import com.easydiet.backend.domain.diet.enums.Goal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutritionTarget {

    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;

    private Goal goal;
}
