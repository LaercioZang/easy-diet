package com.easydiet.backend.mapper;

import com.easydiet.backend.dto.user.UserMeResponse;
import com.easydiet.backend.persistence.user.UserEntity;

public final class UserMapper {

    private UserMapper() {}

    public static UserMeResponse toMeResponse(UserEntity entity) {
        return new UserMeResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCreatedAt()
        );
    }
}
