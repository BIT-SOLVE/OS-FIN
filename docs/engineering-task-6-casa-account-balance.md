# Engineering Task 6: CASA Account and Balance Foundation

## Summary
Task 6 implemented the CASA (Current and Savings Account) account and balance foundation. This module enables the opening of accounts for approved customers, manages account lifecycle states, and maintains multi-dimensional balances.

## Implemented Components

### Backend
- **Account Module**: Created `com.ufos.platform.modules.account` with support for:
    - **Accounts**: Core account records linked to customers and products.
    - **Account Balances**: Tracking of ledger, available, blocked, and uncleared balances.
    - **Account Blocks**: Placing and releasing holds on funds.
    - **Lifecycle History**: Audit trail for account status changes.
- **Balance Logic**: Implemented deterministic calculation for available balance:
    - `Available = Ledger + Overdraft - Blocked - Uncleared`.
- **Lifecycle Management**: Support for `ACTIVE`, `FROZEN`, and `CLOSED` states.
- **REST APIs**:
    - `/api/v1/accounts` (CRUD + Search)
    - `/api/v1/accounts/{id}/balance`
    - `/api/v1/accounts/{id}/freeze` / `/unfreeze` / `/close`
    - `/api/v1/accounts/{id}/blocks`
- **Role-Based Access Control**:
    - `UFOS_ADMIN`: Full access including account closure.
    - `UFOS_OPERATOR`: Open, update, freeze, and manage blocks.
    - `UFOS_AUDITOR`, `UFOS_VIEWER`: Read-only access to accounts and balances.

### Frontend
- **Account Navigation**: Added link for Accounts.
- **Account Screen**: List view for viewing all accounts.
- **Account API Client**: Centralized functions for account, balance, and lifecycle management.

## Seed Data
- Sample CASA account (`A000000001`) with zero balance for the seeded customer.

## Verification
- Backend tests pass for account opening and balance calculation rules.
- Frontend builds successfully with new account management features.
