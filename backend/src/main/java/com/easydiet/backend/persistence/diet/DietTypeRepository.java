package com.easydiet.backend.persistence.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DietTypeRepository extends JpaRepository<DietTypeEntity, UUID> {

    Optional<DietTypeEntity> findByCode(DietCode code);
    Page<DietTypeEntity> findByActiveTrue(Pageable pageable);
    Page<DietTypeEntity> findByActiveFalse(Pageable pageable);
    List<DietTypeEntity> findByActiveTrue();

    List<DietTypeEntity> findByActiveFalse();

    List<DietTypeEntity> findByNameContainingIgnoreCase(String name);
    Page<DietTypeEntity> findByActiveAndNameContainingIgnoreCase(Boolean active,String name,Pageable pageable);

}
