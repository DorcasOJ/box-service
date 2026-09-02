CREATE TABLE IF NOT EXISTS items (
   id UUID PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   txref VARCHAR(20) NOT NULL UNIQUE,
   battery_level INTEGER NOT NULL,
   state VARCHAR(20) NOT NULL,
   version BIGINT NOT NULL DEFAULT 0,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
--    deleted_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);
CREATE INDEX idx_users_id ON box(id);
CREATE INDEX idx_users_id ON box(name);
CREATE INDEX idx_boxes_deleted_at ON users(id, deleted_at);
