package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<FoodEntity, UUID> {

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findByActiveTrue();

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findByFoodCategory_CodeAndActiveTrue(Category categoryCode);

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findByActiveFalse();

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findByFoodCategory_Code(Category category);

    @EntityGraph(attributePaths = "category")
    List<FoodEntity> findAll();
}
