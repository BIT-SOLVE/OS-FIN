# Engineering Task 3: Enterprise Setup Foundation

## Summary
Task 3 implemented the enterprise setup foundation, providing the organizational structure necessary for the UFOS platform. This includes legal entities, branches, currencies, and business calendars.

## Implemented Components

### Backend
- **Enterprise Module**: Created `com.ufos.platform.modules.enterprise` with support for:
    - **Legal Entities**: Multi-entity support with country and currency configuration.
    - **Branches**: Multi-branch support linked to legal entities.
    - **Currencies**: Reference data for global currencies.
    - **Business Calendars**: Holiday and business day management.
- **Database Tables**: Created Flyway migration `V003__create_enterprise_setup_tables.sql`.
- **REST APIs**:
    - `/api/v1/enterprise/legal-entities`
    - `/api/v1/enterprise/branches`
    - `/api/v1/enterprise/currencies`
    - `/api/v1/enterprise/calendars`
- **Role-Based Access Control**:
    - `UFOS_ADMIN`: Create and update setup data.
    - `UFOS_OPERATOR`, `UFOS_AUDITOR`, `UFOS_VIEWER`: Read-only access.

### Frontend
- **Enterprise Navigation**: Added links for Legal Entities and Currencies.
- **Legal Entity Screen**: List view for managing legal entities.
- **Currency Screen**: List view for viewing available currencies.
- **Enterprise API Client**: Centralized functions for interacting with enterprise endpoints.

## Seed Data
- **Currencies**: USD, EUR, GBP.
- **Legal Entity**: UFOSBANK (US, USD).
- **Branch**: HQ (Head Office).
- **Calendar**: US-USD.

## Verification
- Backend tests pass for legal entity creation and access control.
- Frontend builds successfully with new routing and pages.
