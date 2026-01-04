package com.easydiet.backend.service;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;
import com.easydiet.backend.persistence.food.FoodCategoryRepository;
import com.easydiet.backend.persistence.food.FoodEntity;
import com.easydiet.backend.persistence.food.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodServiceTest {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodCategoryRepository categoryRepository;

    @BeforeEach
    void cleanDatabase() {
        foodRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldReturnOnlyActiveFoods() {
        FoodCategoryEntity category = categoryRepository.save(
            FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(Category.PROTEIN)
                .name("Protein")
                .active(true)
                .build()
        );

        foodRepository.save(
            FoodEntity.builder()
                .id(UUID.randomUUID())
                .name("Chicken Breast")
                .foodCategory(category)
                .calories(120)
                .protein(new BigDecimal("22.5"))
                .carbs(BigDecimal.ZERO)
                .fat(new BigDecimal("1.5"))
                .active(true)
                .build()
        );

        foodRepository.save(
            FoodEntity.builder()
                .id(UUID.randomUUID())
                .name("Old Food")
                .foodCategory(category)
                .calories(100)
                .protein(BigDecimal.ONE)
                .carbs(BigDecimal.ONE)
                .fat(BigDecimal.ONE)
                .active(false)
                .build()
        );

        List<Food> foods = foodService.findAllActive();

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Chicken Breast");
    }

    @Test
    void shouldReturnActiveFoodsByCategoryCode() {
        FoodCategoryEntity protein = categoryRepository.save(
            FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(Category.PROTEIN)
                .name("Protein")
                .active(true)
                .build()
        );

        FoodCategoryEntity carb = categoryRepository.save(
            FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(Category.CARB)
                .name("Carbohydrate")
                .active(true)
                .build()
        );

        foodRepository.save(
            FoodEntity.builder()
                .id(UUID.randomUUID())
                .name("Chicken")
                .foodCategory(protein)
                .calories(120)
                .protein(new BigDecimal("22"))
                .carbs(BigDecimal.ZERO)
                .fat(BigDecimal.ONE)
                .active(true)
                .build()
        );

        foodRepository.save(
            FoodEntity.builder()
                .id(UUID.randomUUID())
                .name("Rice")
                .foodCategory(carb)
                .calories(130)
                .protein(new BigDecimal("2.5"))
                .carbs(new BigDecimal("28"))
                .fat(BigDecimal.ZERO)
                .active(true)
                .build()
        );

        List<Food> foods = foodService.findActiveByCategory(Category.PROTEIN);

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Chicken");
    }
}
