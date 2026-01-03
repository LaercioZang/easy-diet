package com.easydiet.backend.domain.plan;

import com.easydiet.backend.domain.food.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealFood {

    private Food food;
    private int quantityGrams;
}
