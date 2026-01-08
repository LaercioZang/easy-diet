package com.easydiet.backend.mapper;

import com.easydiet.backend.dto.diet.DietPlanSnapshotResponse;
import com.easydiet.backend.persistence.diet.DietPlanEntity;

public final class DietPlanSnapshotMapper {

    private DietPlanSnapshotMapper() {}

    public static DietPlanSnapshotResponse toResponse(DietPlanEntity entity) {
        return new DietPlanSnapshotResponse(
                entity.getId(),
                entity.getTdee(),
                entity.getWeekDistributionJson(),
                entity.getCreatedAt()
        );
    }
}
