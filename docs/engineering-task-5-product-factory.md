# Engineering Task 5: Product Factory Foundation

## Summary
Task 5 implemented the Product Factory foundation, enabling the definition and management of financial products. This module supports product types, products, and configurable rules for interest, fees, and accounting events.

## Implemented Components

### Backend
- **Product Module**: Created `com.ufos.platform.modules.product` with support for:
    - **Product Types**: Categories for products (e.g., CASA, Loans).
    - **Products**: Specific financial offerings with lifecycle management.
    - **Product Rules**: Generic rule storage.
    - **Interest Rules**: Configuration for interest rates, calculation basis, and frequencies.
    - **Fee Rules**: Configuration for charges, events, and calculation methods.
    - **Accounting Rules**: Mapping of business events to GL placeholders.
- **Product Lifecycle**: Implemented a state machine for products:
    - `DRAFT` -> `PENDING_APPROVAL` -> `APPROVED` / `REJECTED`
    - `APPROVED` -> `RETIRED`
- **Product Approval History**: Tracking of lifecycle actions and comments.
- **REST APIs**:
    - `/api/v1/products/types`
    - `/api/v1/products`
    - `/api/v1/products/{id}/interest-rules`
    - `/api/v1/products/{id}/submit`
    - `/api/v1/products/{id}/approve`
    - `/api/v1/products/{id}/reject`
- **Role-Based Access Control**:
    - `UFOS_ADMIN`: Full CRUD + Approval + Retire.
    - `UFOS_OPERATOR`: Create/Edit + Submit for Approval.
    - `UFOS_AUDITOR`, `UFOS_VIEWER`: Read-only access to approved products.

### Frontend
- **Product Navigation**: Added links for Products and Product Types.
- **Product Screens**: List views for managing products and product types.
- **Product API Client**: Centralized functions for product and rule management.

## Seed Data
- Product Types: CASA, TD, PERSONAL_LOAN, PAYMENT.
- Sample Product: `CASA_BASIC_USD` with interest, fee, and accounting rules.

## Verification
- Backend tests pass for product creation and lifecycle state transitions.
- Frontend builds successfully with new product factory features.
