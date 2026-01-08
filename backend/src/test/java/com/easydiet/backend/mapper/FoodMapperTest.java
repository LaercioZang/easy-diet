package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;
import com.easydiet.backend.persistence.food.FoodEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FoodMapperTest {

    @Test
    void shouldMapFoodEntityToDomain() {
        UUID foodId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        FoodCategoryEntity category = FoodCategoryEntity.builder()
            .id(categoryId)
            .code(Category.PROTEIN)
            .name("Protein")
            .active(true)
            .build();

        FoodEntity entity = FoodEntity.builder()
            .id(foodId)
            .name("Chicken Breast")
            .foodCategory(category)
            .calories(BigDecimal.valueOf(120))
            .protein(new BigDecimal("22.5"))
            .carbs(BigDecimal.ZERO)
            .fat(new BigDecimal("1.5"))
            .active(true)
            .build();

        Food domain = FoodMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(foodId);
        assertThat(domain.getName()).isEqualTo("Chicken Breast");
        assertThat(domain.getCalories()).isEqualTo(BigDecimal.valueOf(120));
        assertThat(domain.getProtein()).isEqualTo(new BigDecimal("22.5"));
        assertThat(domain.getCarbs()).isEqualTo(BigDecimal.ZERO);
        assertThat(domain.getFat()).isEqualTo(new BigDecimal("1.5"));
        assertThat(domain.isActive()).isTrue();

        assertThat(domain.getCategory()).isNotNull();
        assertThat(domain.getCategory().getCode()).isEqualTo(Category.PROTEIN);
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertThat(FoodMapper.toDomain(null)).isNull();
    }
}
