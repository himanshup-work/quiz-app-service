INSERT INTO quiz_app_service.question (question_id, question_text, quiz_id)
VALUES (?, ?, ?)
ON CONFLICT (question_id)
DO UPDATE SET
    question_text = EXCLUDED.question_text,
    quiz_id = EXCLUDED.quiz_id;