package com.easydiet.backend.service.user;

import com.easydiet.backend.persistence.user.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity findById(UUID userId);
}
