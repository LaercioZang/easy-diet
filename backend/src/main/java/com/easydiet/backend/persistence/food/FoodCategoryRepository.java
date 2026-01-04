package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodCategoryRepository extends JpaRepository<FoodCategoryEntity, UUID> {

    Optional<FoodCategoryEntity> findByCode(Category code);

    List<FoodCategoryEntity> findByActiveTrue();

    List<FoodCategoryEntity> findByActiveFalse();

    List<FoodCategoryEntity> findByNameContainingIgnoreCase(String name);
}
