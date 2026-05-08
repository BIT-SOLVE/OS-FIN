CREATE TABLE legal_entities (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    legal_name VARCHAR(255),
    registration_number VARCHAR(100),
    tax_identifier VARCHAR(100),
    country_code CHAR(2) NOT NULL,
    base_currency_code CHAR(3) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE branches (
    id UUID PRIMARY KEY,
    legal_entity_id UUID NOT NULL REFERENCES legal_entities(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    branch_type VARCHAR(50),
    country_code CHAR(2) NOT NULL,
    city VARCHAR(100),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    postal_code VARCHAR(50),
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0,
    UNIQUE(legal_entity_id, code)
);

CREATE TABLE currencies (
    code CHAR(3) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    numeric_code VARCHAR(3),
    minor_units INT NOT NULL DEFAULT 2,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL
);

CREATE TABLE business_calendars (
    id UUID PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    country_code CHAR(2),
    currency_code CHAR(3),
    description TEXT,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE calendar_holidays (
    id UUID PRIMARY KEY,
    calendar_id UUID NOT NULL REFERENCES business_calendars(id),
    holiday_date DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    holiday_type VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(calendar_id, holiday_date)
);

-- Seed Reference Data
INSERT INTO currencies (code, name, numeric_code, minor_units, status) VALUES
('USD', 'US Dollar', '840', 2, 'ACTIVE'),
('EUR', 'Euro', '978', 2, 'ACTIVE'),
('GBP', 'British Pound', '826', 2, 'ACTIVE');

INSERT INTO legal_entities (id, code, name, country_code, base_currency_code, status) VALUES
('00000000-0000-0000-0003-000000000001', 'UFOSBANK', 'UFOS Bank', 'US', 'USD', 'ACTIVE');

INSERT INTO branches (id, legal_entity_id, code, name, country_code, status) VALUES
('00000000-0000-0000-0004-000000000001', '00000000-0000-0000-0003-000000000001', 'HQ', 'Head Office', 'US', 'ACTIVE');

INSERT INTO business_calendars (id, code, name, country_code, currency_code, status) VALUES
('00000000-0000-0000-0005-000000000001', 'US-USD', 'US USD Business Calendar', 'US', 'USD', 'ACTIVE');
