# Engineering Task 2: IAM and Keycloak Integration Foundation

## Summary
Task 2 integrated Keycloak as the Identity and Access Management (IAM) provider for the UFOS platform. This established the foundation for user authentication, role-based access control (RBAC), and secure API communication.

## Implemented Components

### Infrastructure
- **Keycloak Service**: Added to `docker-compose.yml` with a pre-configured realm import.
- **Realm Configuration**: Created `deploy/keycloak/realm-ufos.json` defining the `ufos` realm, clients (`ufos-web`, `ufos-api`), roles (`UFOS_ADMIN`, `UFOS_OPERATOR`, `UFOS_AUDITOR`, `UFOS_VIEWER`), and test users.

### Backend
- **OAuth2 Resource Server**: Configured Spring Security to validate JWT tokens issued by Keycloak.
- **Role Mapping**: Implemented `KeycloakJwtAuthenticationConverter` to map Keycloak realm roles to Spring Security `ROLE_` authorities.
- **IAM Database Tables**: Created Flyway migration `V002__create_iam_tables.sql` for `users`, `roles`, and `permissions` (with seed data).
- **IAM APIs**:
    - `GET /api/v1/iam/me`: Returns current user info and syncs Keycloak user data to the local database.
    - `GET /api/v1/iam/roles`: Returns available roles (admin/auditor only).
    - `GET /api/v1/iam/permissions`: Returns available permissions (admin/auditor only).
- **Security Configuration**: Secured all `/api/v1/**` endpoints except health checks and OpenAPI docs.
- **OpenAPI**: Updated to support Bearer JWT authentication in Swagger UI.

### Frontend
- **Keycloak Integration**: Added `keycloak-js` and implemented initialization and authentication hooks in `frontend/src/auth/keycloak.ts`.
- **API Client**: Enhanced `apiClient` to automatically attach the JWT token to request headers.
- **Auth UI**: Added `UserStatus` component to handle login/logout and display user profile information.

## Test Users
| Username | Password | Roles |
| :--- | :--- | :--- |
| admin.user | password | UFOS_ADMIN |
| operator.user | password | UFOS_OPERATOR |
| auditor.user | password | UFOS_AUDITOR |
| viewer.user | password | UFOS_VIEWER |

## Verification
- Backend tests pass with mock JWT.
- Frontend builds successfully.
- Docker Compose configuration includes Keycloak with automatic realm import.
