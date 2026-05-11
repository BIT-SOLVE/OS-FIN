CREATE TABLE app_metadata (
    id UUID PRIMARY KEY,
    app_name VARCHAR(100) NOT NULL,
    app_version VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO app_metadata (id, app_name, app_version)
VALUES ('00000000-0000-0000-0000-000000000001', 'ufos-platform', '0.1.0');
