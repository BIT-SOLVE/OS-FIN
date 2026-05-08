CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    customer_id UUID NOT NULL REFERENCES customers(id),
    party_id UUID NOT NULL REFERENCES parties(id),
    legal_entity_id UUID NOT NULL REFERENCES legal_entities(id),
    branch_id UUID REFERENCES branches(id),
    product_id UUID NOT NULL REFERENCES products(id),
    currency_code CHAR(3) NOT NULL REFERENCES currencies(code),
    account_type VARCHAR(50) NOT NULL,
    account_name VARCHAR(255) NOT NULL,
    account_status VARCHAR(30) NOT NULL,
    lifecycle_status VARCHAR(30) NOT NULL,
    opening_date DATE NOT NULL,
    closing_date DATE NULL,
    freeze_reason TEXT NULL,
    closure_reason TEXT NULL,
    overdraft_limit NUMERIC(19,4) NOT NULL DEFAULT 0,
    minimum_balance NUMERIC(19,4) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE account_balances (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL UNIQUE REFERENCES accounts(id),
    currency_code CHAR(3) NOT NULL REFERENCES currencies(code),
    ledger_balance NUMERIC(19,4) NOT NULL DEFAULT 0,
    available_balance NUMERIC(19,4) NOT NULL DEFAULT 0,
    blocked_balance NUMERIC(19,4) NOT NULL DEFAULT 0,
    uncleared_balance NUMERIC(19,4) NOT NULL DEFAULT 0,
    overdraft_available NUMERIC(19,4) NOT NULL DEFAULT 0,
    last_updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE account_blocks (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id),
    block_reference VARCHAR(100) UNIQUE NOT NULL,
    block_amount NUMERIC(19,4) NOT NULL,
    block_reason VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    released_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    released_by VARCHAR(100)
);

CREATE TABLE account_lifecycle_history (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id),
    action VARCHAR(50) NOT NULL,
    previous_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    reason TEXT,
    acted_by VARCHAR(100),
    acted_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_accounts_number ON accounts(account_number);
CREATE INDEX idx_accounts_customer ON accounts(customer_id);
CREATE INDEX idx_accounts_party ON accounts(party_id);
CREATE INDEX idx_accounts_le ON accounts(legal_entity_id);
CREATE INDEX idx_accounts_branch ON accounts(branch_id);
CREATE INDEX idx_accounts_product ON accounts(product_id);
CREATE INDEX idx_accounts_status ON accounts(account_status);
CREATE INDEX idx_accounts_lifecycle ON accounts(lifecycle_status);
CREATE INDEX idx_account_balances_account ON account_balances(account_id);
CREATE INDEX idx_account_blocks_account_status ON account_blocks(account_id, status);

-- Seed Data: Sample CASA Account
INSERT INTO accounts (id, account_number, customer_id, party_id, legal_entity_id, branch_id, product_id, currency_code, account_type, account_name, account_status, lifecycle_status, opening_date) VALUES
('00000000-0000-0000-0014-000000000001', 'A000000001', '00000000-0000-0000-0007-000000000001', '00000000-0000-0000-0006-000000000001', '00000000-0000-0000-0003-000000000001', '00000000-0000-0000-0004-000000000001', '00000000-0000-0000-0010-000000000001', 'USD', 'CASA', 'Jane Sample Basic USD CASA', 'ACTIVE', 'OPEN', CURRENT_DATE);

INSERT INTO account_balances (id, account_id, currency_code, ledger_balance, available_balance, blocked_balance, uncleared_balance, overdraft_available) VALUES
('00000000-0000-0000-0015-000000000001', '00000000-0000-0000-0014-000000000001', 'USD', 0, 0, 0, 0, 0);

INSERT INTO account_lifecycle_history (id, account_id, action, previous_status, new_status, reason, acted_by) VALUES
('00000000-0000-0000-0016-000000000001', '00000000-0000-0000-0014-000000000001', 'OPEN', NULL, 'OPEN', 'Initial account opening', 'SYSTEM');
