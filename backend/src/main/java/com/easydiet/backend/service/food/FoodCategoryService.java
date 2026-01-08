package com.easydiet.backend.service.food;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.dto.FoodCategoryRequest;
import com.easydiet.backend.dto.FoodCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FoodCategoryService {
    List<FoodCategory> findAll();
    Page<FoodCategoryResponse> findAll(Pageable pageable);
    
    List<FoodCategory> findAllActive();
    List<FoodCategory> findAll(Boolean active, String search);
    FoodCategory findById(UUID id);
    FoodCategory create(FoodCategoryRequest request);
    FoodCategory update(UUID id, FoodCategoryRequest request);
    void delete(UUID id);
}
 