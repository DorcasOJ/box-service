CREATE TABLE IF NOT EXISTS boxes (
   id UUID PRIMARY KEY,
   txref VARCHAR(20) NOT NULL UNIQUE,
   battery_level INTEGER NOT NULL,
   state VARCHAR(20) NOT NULL,
   max_weight BIGINT NOT NULL DEFAULT 5000,
   current_weight BIGINT NOT NULL DEFAULT 0,
   camera_enabled BOOLEAN NOT NULL DEFAULT TRUE,
   version BIGINT NOT NULL DEFAULT 0,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
--    deleted_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at TIMESTAMP
);
CREATE INDEX idx_boxes_id ON boxes(id);
CREATE INDEX idx_boxes_deleted_at ON boxes(id, deleted_at);
