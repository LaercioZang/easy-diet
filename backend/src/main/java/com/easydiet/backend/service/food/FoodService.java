package com.easydiet.backend.service.food;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {

    List<Food> findAllActive();

    List<Food> findActiveByCategory(Category category);

    Page<Food> findAll(Pageable pageable);

    List<Food> findAll(
            Boolean active,
            String search,
            Category category
    );
}
