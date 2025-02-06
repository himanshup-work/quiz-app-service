BEGIN TRANSACTION;

-- Insert demo categories
INSERT INTO quiz_app_service.category (category_id, name, description) VALUES
(1, 'Science', 'Science-related quizzes'),
(2, 'History', 'Quizzes about historical events'),
(3, 'Mathematics', 'Quizzes about math topics'),
(4, 'Technology', 'Tech-related quizzes');

COMMIT TRANSACTION;