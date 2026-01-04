package com.easydiet.backend.persistence.food;

import com.easydiet.backend.domain.food.enums.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private FoodCategoryRepository repository;

    @Test
    void shouldFindCategoryByCode() {
        var result = repository.findByCode(Category.PROTEIN);

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo(Category.PROTEIN);
    }
}
