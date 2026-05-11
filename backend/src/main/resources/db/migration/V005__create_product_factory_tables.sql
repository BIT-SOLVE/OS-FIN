CREATE TABLE product_types (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    product_family VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE products (
    id UUID PRIMARY KEY,
    product_code VARCHAR(50) UNIQUE NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_type_id UUID NOT NULL REFERENCES product_types(id),
    legal_entity_id UUID REFERENCES legal_entities(id),
    currency_code CHAR(3) REFERENCES currencies(code),
    product_family VARCHAR(50) NOT NULL,
    product_category VARCHAR(50),
    lifecycle_status VARCHAR(30) NOT NULL,
    effective_from DATE,
    effective_to DATE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE product_rules (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    rule_type VARCHAR(50) NOT NULL,
    rule_code VARCHAR(100) NOT NULL,
    rule_name VARCHAR(255) NOT NULL,
    rule_value TEXT,
    rule_json JSONB,
    status VARCHAR(30) NOT NULL,
    effective_from DATE,
    effective_to DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0,
    UNIQUE(product_id, rule_type, rule_code)
);

CREATE TABLE interest_rules (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    rule_code VARCHAR(100) NOT NULL,
    interest_type VARCHAR(50) NOT NULL,
    rate_type VARCHAR(50) NOT NULL,
    fixed_rate NUMERIC(19,8),
    index_code VARCHAR(50),
    spread_rate NUMERIC(19,8),
    calculation_basis VARCHAR(50),
    compounding_frequency VARCHAR(50),
    accrual_frequency VARCHAR(50),
    status VARCHAR(30) NOT NULL,
    effective_from DATE,
    effective_to DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0,
    UNIQUE(product_id, rule_code)
);

CREATE TABLE fee_rules (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    rule_code VARCHAR(100) NOT NULL,
    fee_type VARCHAR(50) NOT NULL,
    fee_amount NUMERIC(19,4),
    fee_rate NUMERIC(19,8),
    currency_code CHAR(3) REFERENCES currencies(code),
    charge_event VARCHAR(50),
    calculation_method VARCHAR(50),
    status VARCHAR(30) NOT NULL,
    effective_from DATE,
    effective_to DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0,
    UNIQUE(product_id, rule_code)
);

CREATE TABLE accounting_rules (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    rule_code VARCHAR(100) NOT NULL,
    accounting_event VARCHAR(100) NOT NULL,
    debit_gl_placeholder VARCHAR(100),
    credit_gl_placeholder VARCHAR(100),
    description TEXT,
    status VARCHAR(30) NOT NULL,
    effective_from DATE,
    effective_to DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0,
    UNIQUE(product_id, rule_code)
);

CREATE TABLE product_approval_history (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    action VARCHAR(50) NOT NULL,
    previous_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,
    comment TEXT,
    acted_by VARCHAR(100),
    acted_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_products_code ON products(product_code);
CREATE INDEX idx_products_type ON products(product_type_id);
CREATE INDEX idx_products_le ON products(legal_entity_id);
CREATE INDEX idx_products_currency ON products(currency_code);
CREATE INDEX idx_products_status ON products(lifecycle_status);
CREATE INDEX idx_product_rules_product_type ON product_rules(product_id, rule_type);
CREATE INDEX idx_interest_rules_product ON interest_rules(product_id);
CREATE INDEX idx_fee_rules_product ON fee_rules(product_id);
CREATE INDEX idx_accounting_rules_product ON accounting_rules(product_id);

-- Seed Data: Product Types
INSERT INTO product_types (id, code, name, product_family, status) VALUES
('00000000-0000-0000-0009-000000000001', 'CASA', 'Current and Savings Account', 'DEPOSIT', 'ACTIVE'),
('00000000-0000-0000-0009-000000000002', 'TD', 'Term Deposit', 'DEPOSIT', 'ACTIVE'),
('00000000-0000-0000-0009-000000000003', 'PERSONAL_LOAN', 'Personal Loan', 'LENDING', 'ACTIVE'),
('00000000-0000-0000-0009-000000000004', 'PAYMENT', 'Payment Product', 'PAYMENTS', 'ACTIVE');

-- Seed Data: Sample CASA Product
INSERT INTO products (id, product_code, product_name, product_type_id, legal_entity_id, currency_code, product_family, product_category, lifecycle_status) VALUES
('00000000-0000-0000-0010-000000000001', 'CASA_BASIC_USD', 'Basic USD CASA', '00000000-0000-0000-0009-000000000001', '00000000-0000-0000-0003-000000000001', 'USD', 'DEPOSIT', 'CASA', 'APPROVED');

INSERT INTO interest_rules (id, product_id, rule_code, interest_type, rate_type, fixed_rate, calculation_basis, compounding_frequency, accrual_frequency, status) VALUES
('00000000-0000-0000-0011-000000000001', '00000000-0000-0000-0010-000000000001', 'INT_STD', 'CREDIT', 'FIXED', 0.01, 'ACT_360', 'MONTHLY', 'DAILY', 'ACTIVE');

INSERT INTO fee_rules (id, product_id, rule_code, fee_type, fee_amount, currency_code, charge_event, calculation_method, status) VALUES
('00000000-0000-0000-0012-000000000001', '00000000-0000-0000-0010-000000000001', 'FEE_MNT', 'MAINTENANCE', 5.00, 'USD', 'MONTHLY_CLOSE', 'FLAT', 'ACTIVE');

INSERT INTO accounting_rules (id, product_id, rule_code, accounting_event, debit_gl_placeholder, credit_gl_placeholder, status) VALUES
('00000000-0000-0000-0013-000000000001', '00000000-0000-0000-0010-000000000001', 'ACC_OPEN', 'ACCOUNT_OPENING', 'CASH_ON_HAND', 'CUSTOMER_DEPOSITS', 'ACTIVE');
