package com.easydiet.backend.persistence.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodItemRepository extends JpaRepository<FoodItemEntity, UUID> {

    List<FoodItemEntity> findAllByMealId(UUID mealId);
}
