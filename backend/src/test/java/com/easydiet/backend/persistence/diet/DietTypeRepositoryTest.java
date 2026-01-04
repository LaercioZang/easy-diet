package com.easydiet.backend.persistence.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
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
class DietTypeRepositoryTest {

    @Autowired
    private DietTypeRepository repository;

    @Test
    @DisplayName("Should find diet type by code")
    void shouldFindDietTypeByCode() {
        repository.save(createDietType(DietCode.KETO, "Ketogenic", true));

        var result = repository.findByCode(DietCode.KETO);

        assertThat(result).isPresent();
        assertThat(result.get().getCode()).isEqualTo(DietCode.KETO);
    }

    @Test
    @DisplayName("Should find only active diet types")
    void shouldFindOnlyActiveDietTypes() {
        repository.save(createDietType(DietCode.KETO, "Ketogenic", true));
        repository.save(createDietType(DietCode.VEGAN, "Vegan", false));

        List<DietTypeEntity> activeDietTypes = repository.findByActiveTrue();

        assertThat(activeDietTypes).hasSize(1);
        assertThat(activeDietTypes.get(0).isActive()).isTrue();
        assertThat(activeDietTypes.get(0).getCode()).isEqualTo(DietCode.KETO);
    }

    private DietTypeEntity createDietType(DietCode code, String name, boolean active) {
        return DietTypeEntity.builder()
                .id(UUID.randomUUID())
                .code(code)
                .name(name)
                .active(active)
                .build();
    }
}
