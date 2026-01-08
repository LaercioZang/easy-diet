package com.easydiet.backend.service.food;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;

import java.util.List;

public interface FoodService {

    List<Food> findAllActive();

    List<Food> findActiveByCategory(Category category);

    List<Food> findAll(
            Boolean active,
            String search,
            Category category
    );
}
