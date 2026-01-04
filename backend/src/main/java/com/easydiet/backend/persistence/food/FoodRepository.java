package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<FoodEntity, UUID> {

    List<FoodEntity> findByActiveTrue();

    List<FoodEntity> findByFoodCategory_CodeAndActiveTrue(Category categoryCode);

    List<FoodEntity> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    
    List<FoodEntity> findByActiveFalse();

    List<FoodEntity> findByNameContainingIgnoreCase(String name);

    List<FoodEntity> findByFoodCategory_Code(Category category);
}
