SELECT user_id, first_name, last_name, email, user_name, password, bio, image, role
FROM users
WHERE user_id = ?;