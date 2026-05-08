package com.ufos.platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ufosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UFOS Platform API")
                        .description("Unified Financial Operating System Platform API")
                        .version("0.1.0"));
    }
}
