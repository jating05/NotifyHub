CREATE TABLE channels (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    expiry_time INT NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('ENABLED', 'DISABLED')),
    notification_type VARCHAR(50)[] NOT NULL DEFAULT ARRAY['SMS', 'EMAIL'],
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);