-- UPSERT statement for PostgreSQL
INSERT INTO users (user_id, first_name, last_name, email, user_name, bio, password, image, role)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (user_id)  -- Assuming user_id is the unique identifier
DO UPDATE SET
    first_name = EXCLUDED.first_name,
    last_name = EXCLUDED.last_name,
    email = EXCLUDED.email,
    user_name = EXCLUDED.user_name,
    bio = EXCLUDED.bio,
    image = EXCLUDED.image;
