SELECT user_id, full_name, email, user_name, password, image, role
FROM users
WHERE email = ?
OR user_name = ?;