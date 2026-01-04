package com.easydiet.backend.service;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.persistence.food.FoodEntity;

import java.util.List;

public interface FoodService {

    List<Food> findAllActive();

    List<Food> findActiveByCategory(Category category);
}
