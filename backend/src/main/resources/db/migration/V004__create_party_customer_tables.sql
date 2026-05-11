CREATE TABLE parties (
    id UUID PRIMARY KEY,
    party_number VARCHAR(50) UNIQUE NOT NULL,
    party_type VARCHAR(30) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    legal_name VARCHAR(255),
    first_name VARCHAR(100),
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    date_of_birth DATE,
    incorporation_date DATE,
    country_of_residence CHAR(2),
    country_of_incorporation CHAR(2),
    tax_identifier VARCHAR(100),
    email VARCHAR(255),
    phone_number VARCHAR(50),
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE customers (
    id UUID PRIMARY KEY,
    customer_number VARCHAR(50) UNIQUE NOT NULL,
    party_id UUID NOT NULL REFERENCES parties(id),
    legal_entity_id UUID NOT NULL REFERENCES legal_entities(id),
    branch_id UUID REFERENCES branches(id),
    customer_type VARCHAR(50) NOT NULL,
    customer_segment VARCHAR(50),
    risk_rating VARCHAR(30) NOT NULL,
    onboarding_status VARCHAR(30) NOT NULL,
    customer_status VARCHAR(30) NOT NULL,
    relationship_manager VARCHAR(100),
    opened_at TIMESTAMP NULL,
    closed_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE kyc_profiles (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL UNIQUE REFERENCES customers(id),
    kyc_status VARCHAR(30) NOT NULL,
    risk_score NUMERIC(10,2),
    pep_flag BOOLEAN NOT NULL DEFAULT FALSE,
    sanctions_screening_status VARCHAR(30),
    adverse_media_status VARCHAR(30),
    source_of_funds VARCHAR(255),
    source_of_wealth VARCHAR(255),
    expected_monthly_turnover NUMERIC(19,2),
    expected_monthly_transaction_count INT,
    last_review_date DATE,
    next_review_date DATE,
    approved_by VARCHAR(100),
    approved_at TIMESTAMP NULL,
    rejection_reason TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE party_addresses (
    id UUID PRIMARY KEY,
    party_id UUID NOT NULL REFERENCES parties(id),
    address_type VARCHAR(50) NOT NULL,
    country_code CHAR(2) NOT NULL,
    city VARCHAR(100),
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    postal_code VARCHAR(50),
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL
);

CREATE TABLE party_identifications (
    id UUID PRIMARY KEY,
    party_id UUID NOT NULL REFERENCES parties(id),
    identification_type VARCHAR(50) NOT NULL,
    identification_number VARCHAR(100) NOT NULL,
    issuing_country CHAR(2),
    issue_date DATE,
    expiry_date DATE,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    UNIQUE(party_id, identification_type, identification_number)
);

CREATE INDEX idx_parties_number ON parties(party_number);
CREATE INDEX idx_parties_display_name ON parties(display_name);
CREATE INDEX idx_parties_email ON parties(email);
CREATE INDEX idx_customers_number ON customers(customer_number);
CREATE INDEX idx_customers_party_id ON customers(party_id);
CREATE INDEX idx_customers_legal_entity_id ON customers(legal_entity_id);
CREATE INDEX idx_customers_status ON customers(customer_status);
CREATE INDEX idx_customers_onboarding_status ON customers(onboarding_status);
CREATE INDEX idx_kyc_profiles_customer_id ON kyc_profiles(customer_id);
CREATE INDEX idx_kyc_profiles_status ON kyc_profiles(kyc_status);

-- Seed Data: Sample Individual Party and Customer
INSERT INTO parties (id, party_number, party_type, display_name, first_name, last_name, country_of_residence, status) VALUES
('00000000-0000-0000-0006-000000000001', 'P000000001', 'INDIVIDUAL', 'Jane Sample', 'Jane', 'Sample', 'US', 'ACTIVE');

INSERT INTO customers (id, customer_number, party_id, legal_entity_id, branch_id, customer_type, risk_rating, onboarding_status, customer_status) VALUES
('00000000-0000-0000-0007-000000000001', 'C000000001', '00000000-0000-0000-0006-000000000001', '00000000-0000-0000-0003-000000000001', '00000000-0000-0000-0004-000000000001', 'RETAIL', 'LOW', 'APPROVED', 'ACTIVE');

INSERT INTO kyc_profiles (id, customer_id, kyc_status) VALUES
('00000000-0000-0000-0008-000000000001', '00000000-0000-0000-0007-000000000001', 'APPROVED');
