package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private FoodCategoryRepository repository;

    @Test
    @DisplayName("Should find category by code")
    void shouldFindCategoryByCode() {
        repository.save(createCategory(Category.PROTEIN, "Protein", true));

        var result = repository.findByCode(Category.PROTEIN);

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo(Category.PROTEIN);
    }

    @Test
    @DisplayName("Should find only active categories")
    void shouldFindOnlyActiveCategories() {
        repository.save(createCategory(Category.PROTEIN, "Protein", true));
        repository.save(createCategory(Category.CARB, "Carbohydrate", false));

        List<FoodCategoryEntity> activeCategories = repository.findByActiveTrue();

        assertThat(activeCategories).hasSize(1);
        assertThat(activeCategories.get(0).isActive()).isTrue();
        assertThat(activeCategories.get(0).getCode()).isEqualTo(Category.PROTEIN);
    }

    private FoodCategoryEntity createCategory(Category code, String name, boolean active) {
        return FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(code)
                .name(name)
                .active(active)
                .build();
    }
}
