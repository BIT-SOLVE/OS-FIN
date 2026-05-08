package com.ufos.platform.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class TestSecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "test")
    public JwtDecoder jwtDecoder() {
        return token -> {
            throw new UnsupportedOperationException("Mock JwtDecoder for tests");
        };
    }
}
