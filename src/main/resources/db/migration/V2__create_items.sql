CREATE TABLE IF NOT EXISTS items (
   id UUID PRIMARY KEY,
   box_id UUID,
   name VARCHAR(255) NOT NULL,
   weight INTEGER NOT NULL,
   code VARCHAR(255) NOT NULL UNIQUE,


    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
--    deleted_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL,

   CONSTRAINT fk_item_box
         FOREIGN KEY (box_id)
         REFERENCES boxes(id)
);
CREATE INDEX idx_items_id ON items(id);
CREATE INDEX idx_items_name ON items(name);
CREATE INDEX idx_items_deleted_at ON items(id, deleted_at);
