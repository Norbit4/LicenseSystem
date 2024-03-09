CREATE TABLE IF NOT EXISTS license_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner VARCHAR(255),
    license_key VARCHAR(36) NOT NULL,
    description VARCHAR(255),
    creation_date BIGINT,
    expiration_date BIGINT,
    last_active BIGINT,
    license_type VARCHAR(20) NOT NULL,
    server_key VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS token_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    access_token VARCHAR(36) NOT NULL,
    creation_date BIGINT,
    token_type VARCHAR(20) NOT NULL
);