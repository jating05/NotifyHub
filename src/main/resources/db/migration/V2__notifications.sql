CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    recipient VARCHAR(255) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    notification_type VARCHAR(50) NOT NULL CHECK (notification_type IN ('SMS', 'EMAIL', 'WHATSAPP')),
    status VARCHAR(50) NOT NULL CHECK (status IN ('FAILED', 'SUCCESS', 'IN_PROGRESS')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    channel_id INTEGER NOT NULL,
    CONSTRAINT fk_notifications_channels FOREIGN KEY (channel_id) REFERENCES channels(id)
);
