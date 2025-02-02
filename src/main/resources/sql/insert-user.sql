-- UPSERT statement for PostgreSQL
INSERT INTO users (user_id, full_name, email, user_name, password, image, role)
VALUES (?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (user_id)  -- Assuming user_id is the unique identifier
DO UPDATE SET
    full_name = EXCLUDED.full_name,
    email = EXCLUDED.email,
    user_name = EXCLUDED.user_name,
    password = EXCLUDED.password,
    image = EXCLUDED.image,
    role = EXCLUDED.role;
