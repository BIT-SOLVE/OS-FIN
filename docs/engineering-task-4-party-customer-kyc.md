# Engineering Task 4: Party and Customer Master Foundation

## Summary
Task 4 implemented the Party and Customer Master foundation, including KYC profile management. This module allows for the management of personal and corporate data for customers and other parties, linked to the enterprise structure.

## Implemented Components

### Backend
- **Party Module**: Created `com.ufos.platform.modules.party` with support for:
    - **Parties**: Reusable base records for individuals and organizations.
    - **Customers**: Financial service users linked to parties and legal entities.
    - **KYC Profiles**: Compliance data including risk rating and approval status.
    - **Addresses & Identifications**: Supplemental data for parties.
- **Onboarding**: Atomic service for onboarding customers (party + customer + KYC).
- **Number Generation**: Automatic generation of unique party (P000000001) and customer (C000000001) numbers.
- **REST APIs**:
    - `/api/v1/party/parties`
    - `/api/v1/party/customers`
    - `/api/v1/party/customers/{customerId}/kyc`
- **Role-Based Access Control**:
    - `UFOS_ADMIN`: Full CRUD + KYC Approval.
    - `UFOS_OPERATOR`: Full CRUD, no KYC Approval.
    - `UFOS_AUDITOR`, `UFOS_VIEWER`: Read-only access.

### Frontend
- **Party Navigation**: Added link for Customers.
- **Customer Screen**: List view for managing customers.
- **Party API Client**: Centralized functions for party, customer, and KYC endpoints.

## Seed Data
- Sample individual party (P000000001) and customer (C000000001) with approved KYC.

## Verification
- Backend tests pass for party creation and customer onboarding.
- Frontend builds successfully with new customer management features.
