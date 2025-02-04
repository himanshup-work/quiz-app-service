INSERT INTO quiz_app_service.quiz (
    quiz_id,
    quiz_title,
    category_id,
    quiz_description,
    time_limit,
    passing_score,
    created_by,
    created_at
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)
ON CONFLICT (quiz_id)
DO UPDATE SET
    quiz_title = EXCLUDED.quiz_title,
    category_id = EXCLUDED.category_id,
    quiz_description = EXCLUDED.quiz_description,
    time_limit = EXCLUDED.time_limit,
    passing_score = EXCLUDED.passing_score,
    created_by = EXCLUDED.created_by,
    created_at = EXCLUDED.created_at;