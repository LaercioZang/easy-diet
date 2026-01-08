package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodCategoryRepository categoryRepository;

    private FoodCategoryEntity proteinCategory;
    private FoodCategoryEntity carbCategory;

    @BeforeEach
    void setUp() {
        proteinCategory = categoryRepository.save(
            FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(Category.PROTEIN)
                .name("Protein")
                .active(true)
                .build()
        );

        carbCategory = categoryRepository.save(
            FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(Category.CARB)
                .name("Carbohydrate")
                .active(true)
                .build()
        );
    }

    @Test
    @DisplayName("Should find only active foods")
    void shouldFindOnlyActiveFoods() {
        foodRepository.save(createFood("Active Chicken", proteinCategory, true));
        foodRepository.save(createFood("Inactive Beef", proteinCategory, false));

        List<FoodEntity> activeFoods = foodRepository.findByActiveTrue();

        assertThat(activeFoods).hasSize(1);
        assertThat(activeFoods.get(0).getName()).isEqualTo("Active Chicken");
        assertThat(activeFoods.get(0).isActive()).isTrue();
    }

    @Test
    @DisplayName("Should find active foods by category code")
    void shouldFindActiveFoodsByCategoryCode() {
        foodRepository.save(createFood("Chicken Breast", proteinCategory, true));
        foodRepository.save(createFood("Sweet Potato", carbCategory, true));
        foodRepository.save(createFood("Inactive Chicken", proteinCategory, false));

        List<FoodEntity> proteinFoods = foodRepository.findByFoodCategory_CodeAndActiveTrue(Category.PROTEIN);

        assertThat(proteinFoods).hasSize(1);
        assertThat(proteinFoods.get(0).getName()).isEqualTo("Chicken Breast");
        assertThat(proteinFoods.get(0).getFoodCategory().getCode()).isEqualTo(Category.PROTEIN);
    }

    @Test
    @DisplayName("Should find active foods by name containing ignore case")
    void shouldFindActiveFoodsByNameContainingIgnoreCase() {
        foodRepository.save(createFood("Chicken Breast", proteinCategory, true));
        foodRepository.save(createFood("Grilled Chicken", proteinCategory, true));
        foodRepository.save(createFood("Beef Steak", proteinCategory, true));
        foodRepository.save(createFood("Inactive Chicken", proteinCategory, false));

        List<FoodEntity> chickenFoods = foodRepository.findByNameContainingIgnoreCaseAndActiveTrue("CHICKEN");

        assertThat(chickenFoods).hasSize(2);
        assertThat(chickenFoods).extracting(FoodEntity::getName)
                .containsExactlyInAnyOrder("Chicken Breast", "Grilled Chicken");
    }

    @Test
    @DisplayName("Should return empty list when no active foods match name")
    void shouldReturnEmptyListWhenNoActiveFoodsMatchName() {
        foodRepository.save(createFood("Chicken Breast", proteinCategory, true));

        List<FoodEntity> result = foodRepository.findByNameContainingIgnoreCaseAndActiveTrue("Fish");

        assertThat(result).isEmpty();
    }

    private FoodEntity createFood(String name, FoodCategoryEntity category, boolean active) {
        return FoodEntity.builder()
                .id(UUID.randomUUID())
                .name(name)
                .foodCategory(category)
                .calories(BigDecimal.valueOf(100))
                .protein(BigDecimal.TEN)
                .carbs(BigDecimal.TEN)
                .fat(BigDecimal.TEN)
                .active(active)
                .build();
    }
}
