package com.easydiet.backend.service.auth;

import com.easydiet.backend.dto.auth.LoginRequest;
import com.easydiet.backend.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
