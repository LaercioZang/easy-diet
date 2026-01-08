package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.dto.DietTypeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DietTypeService {
    List<DietType> findAll();
    List<DietType> findAllActive();
    List<DietType> findAll(Boolean active, String search);
    Page<DietType> findAll(Pageable pageable);
    Page<DietType> findAll(Boolean active, Pageable pageable);
    Page<DietType> findAll(Boolean active, String search, Pageable pageable);

    DietType findById(UUID id);
    DietType create(DietTypeRequest request);
    DietType update(UUID id, DietTypeRequest request);
    void delete(UUID id);
}

