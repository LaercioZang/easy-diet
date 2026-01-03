package com.easydiet.backend.domain.food;

import com.easydiet.backend.domain.food.enums.FoodCategory;
import com.easydiet.backend.domain.food.enums.FoodSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    private String id;
    private String name;

    private int calories;
    private int proteinGrams;
    private int carbsGrams;
    private int fatGrams;

    private FoodCategory category;
    private FoodSource source;
}
