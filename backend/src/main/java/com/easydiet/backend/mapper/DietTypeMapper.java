package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.dto.DietTypeResponse;
import com.easydiet.backend.persistence.diet.DietTypeEntity;
import java.util.UUID;

public final class DietTypeMapper {

    private DietTypeMapper() {
    }

    public static DietType toDomain(DietTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        return DietType.builder()
                .id(entity.getId().toString())
                .code(entity.getCode())
                .name(entity.getName())
                .active(entity.isActive())
                .build();
    }

    public static DietTypeResponse toResponse(DietType domain) {
        if (domain == null) {
            return null;
        }
        return DietTypeResponse.builder()
                .id(UUID.fromString(domain.getId()))
                .code(domain.getCode().name())
                .name(domain.getName())
                .active(domain.isActive())
                .build();
    }
}
