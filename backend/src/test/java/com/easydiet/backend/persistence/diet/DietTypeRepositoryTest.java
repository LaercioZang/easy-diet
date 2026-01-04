package com.easydiet.backend.persistence.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DietTypeRepositoryTest {

    @Autowired
    private DietTypeRepository repository;

    @Test
    void shouldFindDietTypeByCode() {
        var result = repository.findByCode(DietCode.KETO);

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo(DietCode.KETO);
    }
}
