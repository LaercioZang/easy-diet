package com.easydiet.backend.service.v1;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;
import com.easydiet.backend.persistence.food.FoodCategoryRepository;
import com.easydiet.backend.persistence.food.FoodEntity;
import com.easydiet.backend.persistence.food.FoodRepository;
import com.easydiet.backend.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class FoodServiceV1Test {

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

    private FoodCategoryEntity createCategory(Category code) {
        return categoryRepository.save(
            FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(code)
                .name(code.name())
                .active(true)
                .build()
        );
    }

    private FoodEntity createFood(
        String name,
        FoodCategoryEntity category,
        boolean active
    ) {
        return foodRepository.save(
            FoodEntity.builder()
                .id(UUID.randomUUID())
                .name(name)
                .foodCategory(category)
                .calories(100)
                .protein(BigDecimal.TEN)
                .carbs(BigDecimal.ONE)
                .fat(BigDecimal.ONE)
                .active(active)
                .build()
        );
    }

    @Test
    void shouldReturnAllFoodsWhenNoFiltersProvided() {
        FoodCategoryEntity protein = createCategory(Category.PROTEIN);

        createFood("Chicken", protein, true);
        createFood("Old Chicken", protein, false);

        List<Food> foods = foodService.findAll(null, null, null);

        assertThat(foods).hasSize(2);
    }

    @Test
    void shouldReturnOnlyActiveFoodsWhenActiveTrue() {
        FoodCategoryEntity protein = createCategory(Category.PROTEIN);

        createFood("Chicken", protein, true);
        createFood("Old Chicken", protein, false);

        List<Food> foods = foodService.findAll(true, null, null);

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Chicken");
    }


    @Test
    void shouldReturnOnlyInactiveFoodsWhenActiveFalse() {
        FoodCategoryEntity protein = createCategory(Category.PROTEIN);

        createFood("Chicken", protein, true);
        createFood("Old Chicken", protein, false);

        List<Food> foods = foodService.findAll(false, null, null);

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Old Chicken");
    }

    @Test
    void shouldSearchFoodsByNameIgnoringCase() {
        FoodCategoryEntity protein = createCategory(Category.PROTEIN);

        createFood("Chicken Breast", protein, true);
        createFood("Rice", protein, true);

        List<Food> foods = foodService.findAll(null, "chicken", null);

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Chicken Breast");
    }

    @Test
    void shouldFilterFoodsByCategory() {
        FoodCategoryEntity protein = createCategory(Category.PROTEIN);
        FoodCategoryEntity carb = createCategory(Category.CARB);

        createFood("Chicken", protein, true);
        createFood("Rice", carb, true);

        List<Food> foods = foodService.findAll(null, null, Category.PROTEIN);

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Chicken");
    }

}
