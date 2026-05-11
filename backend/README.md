# UFOS Backend

Spring Boot 3 application for the Unified Financial Operating System.

## Technology Stack
- Java 21
- Spring Boot 3
- Maven
- PostgreSQL
- Flyway
- Spring Security
- Spring Boot Actuator
- springdoc-openapi (Swagger)

## Development Setup
1. Ensure PostgreSQL is running (use root `docker-compose.yml`).
2. Run `mvn spring-boot:run`.

## Core Features
- Health Check: `/api/v1/health`
- Actuator: `/actuator/health`
- Correlation ID: Every request/response includes `X-Correlation-ID`.
- Global Error Handling: Standardized JSON error responses.
- OpenAPI Documentation: `/swagger-ui.html`.
