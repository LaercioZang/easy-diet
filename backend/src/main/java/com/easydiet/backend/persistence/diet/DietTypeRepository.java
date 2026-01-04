package com.easydiet.backend.persistence.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DietTypeRepository extends JpaRepository<DietTypeEntity, UUID> {

    Optional<DietTypeEntity> findByCode(DietCode code);
}
