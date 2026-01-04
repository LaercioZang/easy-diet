package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FoodCategoryMapperTest {

    @Test
    void shouldMapEntityToDomain() {
        UUID id = UUID.randomUUID();

        FoodCategoryEntity entity = FoodCategoryEntity.builder()
            .id(id)
            .code(Category.PROTEIN)
            .name("Protein")
            .active(true)
            .build();

        FoodCategory domain = FoodCategoryMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(id);
        assertThat(domain.getCode()).isEqualTo(Category.PROTEIN);
        assertThat(domain.getName()).isEqualTo("Protein");
        assertThat(domain.isActive()).isTrue();
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertThat(FoodCategoryMapper.toDomain(null)).isNull();
    }
}
