-- ============================================================
-- SEED BOXES
-- ============================================================

-- 1. Available box
-- IDLE + good battery + plenty of capacity
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'BOX-AVAILABLE-01',
    5000,
    0,
    95,
    'IDLE',
    true
);


-- 2. Available box with some weight already loaded
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'BOX-AVAILABLE-02',
    5000,
    1200,
    80,
    'IDLE',
    true
);


-- 3. Loading box
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '33333333-3333-3333-3333-333333333333',
    'BOX-LOADING-01',
    5000,
    2500,
    72,
    'LOADING',
    true
);


-- 4. Fully loaded box
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '44444444-4444-4444-4444-444444444444',
    'BOX-LOADED-01',
    5000,
    4800,
    90,
    'LOADED',
    true
);


-- 5. Delivering box
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '55555555-5555-5555-5555-555555555555',
    'BOX-DELIVERING-01',
    5000,
    3200,
    65,
    'DELIVERING',
    true
);


-- 6. Delivered box waiting for offloading
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '66666666-6666-6666-6666-666666666666',
    'BOX-DELIVERED-01',
    5000,
    2800,
    55,
    'DELIVERED',
    true
);


-- 7. Returning box
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '77777777-7777-7777-7777-777777777777',
    'BOX-RETURNING-01',
    5000,
    0,
    40,
    'RETURNING',
    false
);


-- 8. IDLE but battery too low to load
-- Useful for testing the 25% battery rule.
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '88888888-8888-8888-8888-888888888888',
    'BOX-LOW-BATTERY',
    5000,
    0,
    20,
    'IDLE',
    true
);


-- 9. Completely full box
-- Useful for capacity testing.
INSERT INTO boxes (
    id,
    txref,
    max_weight,
    current_weight,
    battery_level,
    state,
    camera_enabled
)
VALUES (
    '99999999-9999-9999-9999-999999999999',
    'BOX-FULL-01',
    5000,
    5000,
    88,
    'LOADED',
    true
);


-- ============================================================
-- SEED ITEMS
-- ============================================================

-- Items inside LOADING box

INSERT INTO items (
    id,
    name,
    weight,
    code,
    status,
    box_id
)
VALUES
(
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    'Jollof_Rice',
    1000,
    'FOOD_001',
    'LOADED',
    '33333333-3333-3333-3333-333333333333'
),
(
    'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    'Chicken_Pack',
    1500,
    'FOOD_002',
    'LOADED',
    '33333333-3333-3333-3333-333333333333'
);


-- Items inside LOADED box

INSERT INTO items (
    id,
    name,
    weight,
    code,
    status,
    box_id
)
VALUES
(
    'cccccccc-cccc-cccc-cccc-cccccccccccc',
    'Rice_Bowl',
    1200,
    'ORDER_001',
    'LOADED',
    '44444444-4444-4444-4444-444444444444'
),
(
    'dddddddd-dddd-dddd-dddd-dddddddddddd',
    'Chicken_Box',
    1600,
    'ORDER_002',
    'LOADED',
    '44444444-4444-4444-4444-444444444444'
),
(
    'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    'Drink_Pack',
    1000,
    'ORDER_003',
    'LOADED',
    '44444444-4444-4444-4444-444444444444'
);


-- Items inside DELIVERING box

INSERT INTO items (
    id,
    name,
    weight,
    code,
    status,
    box_id
)
VALUES
(
    'ffffffff-ffff-ffff-ffff-ffffffffffff',
    'Family_Meal',
    2000,
    'ORDER_004',
    'LOADED',
    '55555555-5555-5555-5555-555555555555'
),
(
    '12121212-1212-1212-1212-121212121212',
    'Dessert_Box',
    1200,
    'ORDER_005',
    'LOADED',
    '55555555-5555-5555-5555-555555555555'
);


-- Items inside DELIVERED box

INSERT INTO items (
    id,
    name,
    weight,
    code,
    status,
    box_id
)
VALUES
(
    '13131313-1313-1313-1313-131313131313',
    'Pizza_Box',
    1800,
    'ORDER_006',
    'DELIVERED',
    '66666666-6666-6666-6666-666666666666'
),
(
    '14141414-1414-1414-1414-141414141414',
    'Drink_Crate',
    1000,
    'ORDER_007',
    'DELIVERED',
    '66666666-6666-6666-6666-666666666666'
);