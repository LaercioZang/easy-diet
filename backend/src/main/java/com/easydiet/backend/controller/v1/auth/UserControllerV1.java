package com.easydiet.backend.controller.v1.auth;

import com.easydiet.backend.config.security.SecurityUtils;
import com.easydiet.backend.dto.user.UserMeResponse;
import com.easydiet.backend.mapper.UserMapper;
import com.easydiet.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UserService userService;

    @GetMapping("/me")
    public UserMeResponse me() {

        UUID userId = SecurityUtils.getCurrentUserId();

        return UserMapper.toMeResponse(
                userService.findById(userId)
        );
    }
}
