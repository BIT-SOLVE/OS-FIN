CREATE TABLE users (
    id UUID PRIMARY KEY,
    keycloak_subject VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255),
    display_name VARCHAR(255),
    status VARCHAR(30) NOT NULL,
    last_login_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE permissions (
    id UUID PRIMARY KEY,
    code VARCHAR(150) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL REFERENCES users(id),
    role_id UUID NOT NULL REFERENCES roles(id),
    PRIMARY KEY(user_id, role_id)
);

CREATE TABLE role_permissions (
    role_id UUID NOT NULL REFERENCES roles(id),
    permission_id UUID NOT NULL REFERENCES permissions(id),
    PRIMARY KEY(role_id, permission_id)
);

-- Seed Roles
INSERT INTO roles (id, code, name, description) VALUES
('00000000-0000-0000-0001-000000000001', 'UFOS_ADMIN', 'Administrator', 'Full system access'),
('00000000-0000-0000-0001-000000000002', 'UFOS_OPERATOR', 'Operator', 'Standard operational access'),
('00000000-0000-0000-0001-000000000003', 'UFOS_AUDITOR', 'Auditor', 'Read-only audit access'),
('00000000-0000-0000-0001-000000000004', 'UFOS_VIEWER', 'Viewer', 'Limited read access');

-- Seed Permissions
INSERT INTO permissions (id, code, name, description) VALUES
('00000000-0000-0000-0002-000000000001', 'SYSTEM_ADMIN', 'System Administration', 'Perform system-wide administrative tasks'),
('00000000-0000-0000-0002-000000000002', 'USER_READ', 'Read Users', 'View user information'),
('00000000-0000-0000-0002-000000000003', 'USER_MANAGE', 'Manage Users', 'Create, update, and delete users'),
('00000000-0000-0000-0002-000000000004', 'ROLE_READ', 'Read Roles', 'View role information'),
('00000000-0000-0000-0002-000000000005', 'ROLE_MANAGE', 'Manage Roles', 'Create, update, and delete roles'),
('00000000-0000-0000-0002-000000000006', 'AUDIT_READ', 'Read Audit Logs', 'View audit logs'),
('00000000-0000-0000-0002-000000000007', 'CONFIG_READ', 'Read Configuration', 'View system configuration'),
('00000000-0000-0000-0002-000000000008', 'CONFIG_MANAGE', 'Manage Configuration', 'Update system configuration');

-- Assign Permissions to Roles
-- UFOS_ADMIN: All permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT '00000000-0000-0000-0001-000000000001', id FROM permissions;

-- UFOS_OPERATOR: USER_READ, CONFIG_READ
INSERT INTO role_permissions (role_id, permission_id) VALUES
('00000000-0000-0000-0001-000000000002', '00000000-0000-0000-0002-000000000002'),
('00000000-0000-0000-0001-000000000002', '00000000-0000-0000-0002-000000000007');

-- UFOS_AUDITOR: AUDIT_READ, USER_READ, CONFIG_READ
INSERT INTO role_permissions (role_id, permission_id) VALUES
('00000000-0000-0000-0001-000000000003', '00000000-0000-0000-0002-000000000006'),
('00000000-0000-0000-0001-000000000003', '00000000-0000-0000-0002-000000000002'),
('00000000-0000-0000-0001-000000000003', '00000000-0000-0000-0002-000000000007');

-- UFOS_VIEWER: USER_READ, CONFIG_READ
INSERT INTO role_permissions (role_id, permission_id) VALUES
('00000000-0000-0000-0001-000000000004', '00000000-0000-0000-0002-000000000002'),
('00000000-0000-0000-0001-000000000004', '00000000-0000-0000-0002-000000000007');
