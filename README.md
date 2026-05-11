# Unified Financial Operating System (UFOS)

Unified Financial Operating System (UFOS) is an original, cloud-native, enterprise-grade financial software platform. It combines the functional coverage of a universal core banking system, investor servicing platform, treasury and capital markets platform, and more.

## Legal Note
This is a legally clean, original implementation based on public domain banking and financial principles. No proprietary code or implementation details from other vendors are used.

## Repository Structure
- `/backend`: Spring Boot 3 Java backend
- `/frontend`: React TypeScript Vite frontend
- `/docs`: Engineering and functional documentation
- `/deploy`: Deployment configurations (Docker, Kubernetes)
- `/design`: Design artifacts and specifications

## Prerequisites
- Java 21
- Node.js 18+
- Docker and Docker Compose
- Maven

## How to Run Infrastructure
```bash
docker compose up -d
```
Keycloak Admin Console: `http://localhost:8081` (admin/admin)

## How to Run Backend Locally
```bash
cd backend
mvn spring-boot:run
```
Backend API will be available at `http://localhost:8080/api/v1`
OpenAPI/Swagger UI: `http://localhost:8080/swagger-ui.html`

## How to Run Frontend Locally
```bash
cd frontend
npm install
npm run dev
```
Frontend will be available at `http://localhost:5173`

## How to Run Tests
### Backend
```bash
cd backend
mvn test
```

## Database Migration
Database migrations are handled by Flyway. They run automatically on backend startup.

## Next Development Task
Task 2: IAM and Keycloak Integration Foundation.
