BEGIN TRANSACTION;

-- Insert demo users
INSERT INTO quiz_app_service.users (user_id, full_name, email, user_name, password, image, role) VALUES
('user1', 'Himanshu Pal', 'himanshu@gmail.com', 'himanshu1999', 'himanshu1999', NULL, 'ADMIN'),

-- Insert demo quizzes
INSERT INTO quiz_app_service.quiz (quiz_id, quiz_title, category_id, quiz_description, time_limit, passing_score, created_by, created_at) VALUES
('quiz1', 'Basic Science Quiz', 1, 'A simple science quiz', '00:10:00', 50, 'user1', CURRENT_TIMESTAMP),
('quiz2', 'World History Quiz', 2, 'A quiz about world history', '00:15:00', 60, 'user1', CURRENT_TIMESTAMP);

-- Insert demo questions
INSERT INTO quiz_app_service.question (question_id, question_text, quiz_id) VALUES
('q1', 'What is the chemical symbol for water?', 'quiz1'),
('q2', 'Who discovered gravity?', 'quiz1'),
('q3', 'Who was the first President of the United States?', 'quiz2');

-- Insert demo options
INSERT INTO quiz_app_service.options (option_id, option_text, question_id, is_correct) VALUES
('o1', 'H2O', 'q1', TRUE),
('o2', 'O2', 'q1', FALSE),
('o3', 'CO2', 'q1', FALSE),
('o4', 'NaCl', 'q1', FALSE),
('o5', 'Newton', 'q2', TRUE),
('o6', 'Einstein', 'q2', FALSE),
('o7', 'Galileo', 'q2', FALSE),
('o8', 'Tesla', 'q2', FALSE),
('o9', 'George Washington', 'q3', TRUE),
('o10', 'Abraham Lincoln', 'q3', FALSE),
('o11', 'Thomas Jefferson', 'q3', FALSE),
('o12', 'John Adams', 'q3', FALSE);

COMMIT TRANSACTION;