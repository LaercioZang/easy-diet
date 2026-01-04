package com.easydiet.backend.service;

import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.dto.DietTypeRequest;

import java.util.List;
import java.util.UUID;

public interface DietTypeService {
    List<DietType> findAll();
    List<DietType> findAllActive();
    List<DietType> findAll(Boolean active, String search);
    DietType findById(UUID id);
    DietType create(DietTypeRequest request);
    DietType update(UUID id, DietTypeRequest request);
    void delete(UUID id);
}
