package com.easydiet.backend.persistence.diet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DietPlanRepositoryTest {

    @Autowired
    private DietPlanRepository repository;

    @Test
    void shouldPersistDietPlanSnapshot() {
        DietPlanEntity entity = DietPlanEntity.builder()
                .tdee(2500)
                .weekDistributionJson("{\"test\":true}")
                .createdAt(Instant.now())
                .build();

        DietPlanEntity saved = repository.save(entity);

        assertThat(saved.getId()).isNotNull();
    }
}
