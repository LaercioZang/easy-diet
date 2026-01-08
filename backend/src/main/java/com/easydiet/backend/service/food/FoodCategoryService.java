package com.easydiet.backend.service.food;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.dto.FoodCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface FoodCategoryService {
    List<FoodCategory> findAll();
    List<FoodCategory> findAllActive();
    List<FoodCategory> findAll(Boolean active, String search);
    FoodCategory findById(UUID id);
    FoodCategory create(FoodCategoryRequest request);
    FoodCategory update(UUID id, FoodCategoryRequest request);
    void delete(UUID id);
}
