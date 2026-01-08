package com.easydiet.backend.config;

import com.easydiet.backend.persistence.user.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtTokenProviderTest {

    @Test
    void shouldGenerateAndParseToken() {

        JwtProperties props = mock(JwtProperties.class);
        when(props.getSecret()).thenReturn("12345678901234567890123456789012");
        when(props.getExpirationSeconds()).thenReturn(3600L);

        JwtTokenProvider provider = new JwtTokenProvider(props);

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .email("test@easydiet.com")
                .build();

        String token = provider.generateToken(user);

        assertThat(provider.isValid(token)).isTrue();
        assertThat(provider.getUserId(token)).isEqualTo(user.getId());
    }
}
