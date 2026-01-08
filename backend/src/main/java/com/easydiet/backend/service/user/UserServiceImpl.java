package com.easydiet.backend.service.user;

import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.RESOURCE_NOT_FOUND
                ));
    }
}
