package com.ufos.platform.health;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @Value("${app.version:0.1.0}")
    private String version;

    @GetMapping
    public HealthResponse getHealth() {
        return HealthResponse.builder()
                .status("UP")
                .service("ufos-platform")
                .version(version)
                .build();
    }

    @Data
    @Builder
    public static class HealthResponse {
        private String status;
        private String service;
        private String version;
    }
}
