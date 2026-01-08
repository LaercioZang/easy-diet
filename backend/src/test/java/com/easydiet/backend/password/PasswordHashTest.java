package com.easydiet.backend.password;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;

class PasswordHashTest {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void generateHash() {
        String hash = encoder.encode("123456");
        System.out.println(hash);
    }

    @Test
    void generateSecret() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64 = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64);
    }
}