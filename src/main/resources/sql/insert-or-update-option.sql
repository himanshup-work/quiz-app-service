INSERT INTO quiz_app_service.options (option_id, option_text, question_id, is_correct)
VALUES (?, ?, ?, ?)
ON CONFLICT (option_id)
DO UPDATE SET
    option_text = EXCLUDED.option_text,
    question_id = EXCLUDED.question_id,
    is_correct = EXCLUDED.is_correct;