INSERT INTO users (user_id, full_name, email, user_name, password, image)
VALUES (?, ?, ?, ?, ?, ?)
RETURNING user_id, full_name, email, user_name, password, image;