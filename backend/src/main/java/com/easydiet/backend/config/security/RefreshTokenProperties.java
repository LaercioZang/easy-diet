package com.easydiet.backend.config.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RefreshTokenProperties {

    @Value("${security.refresh.expiration-days}")
    private long expirationDays;
}
