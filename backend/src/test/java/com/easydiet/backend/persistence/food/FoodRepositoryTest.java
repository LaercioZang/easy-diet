package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
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

    @Test
    void shouldFindActiveFoodsByCategoryCode() {
        var category = categoryRepository.save(
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

        var foods = foodRepository.findByFoodCategory_CodeAndActiveTrue(Category.PROTEIN);

        assertThat(foods).hasSize(1);
        assertThat(foods.get(0).getName()).isEqualTo("Chicken Breast");
    }
}
