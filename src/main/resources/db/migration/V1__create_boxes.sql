CREATE TABLE IF NOT EXISTS boxes (
   id UUID PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   txref VARCHAR(20) NOT NULL UNIQUE,
   battery_level INTEGER NOT NULL,
   state VARCHAR(20) NOT NULL,
   version BIGINT NOT NULL DEFAULT 0,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
--    deleted_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at TIMESTAMP NOT NULL
);
CREATE INDEX idx_boxes_id ON boxes(id);
CREATE INDEX idx_boxes_name ON boxes(name);
CREATE INDEX idx_boxes_deleted_at ON boxes(id, deleted_at);
