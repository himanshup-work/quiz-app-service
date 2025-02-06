BEGIN TRANSACTION;

DO $$
BEGIN
    -- User table creation
    CREATE TABLE IF NOT EXISTS quiz_app_service.users (
        user_id VARCHAR(255) PRIMARY KEY NOT NULL,
        full_name VARCHAR(255) NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        user_name VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        image BYTEA,
        role VARCHAR(255) NOT NULL
    );

    -- Quiz category table creation
    CREATE TABLE IF NOT EXISTS quiz_app_service.category (
        category_id INTEGER PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE,
        description VARCHAR(100) NOT NULL
    );

    -- Quiz table creation
    CREATE TABLE IF NOT EXISTS quiz_app_service.quiz (
        quiz_id VARCHAR(255) PRIMARY KEY NOT NULL,
        quiz_title VARCHAR(255) NOT NULL,
        category_id INTEGER NOT NULL,
        quiz_description VARCHAR(255) NOT NULL,
        time_limit TIME NOT NULL,
        passing_score INTEGER NOT NULL,
        created_by VARCHAR(255) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_user FOREIGN KEY (created_by) REFERENCES quiz_app_service.users(user_id),
        CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES quiz_app_service.category(category_id)
    );

    -- Question table creation
    CREATE TABLE IF NOT EXISTS quiz_app_service.question (
        question_id VARCHAR(255) PRIMARY KEY NOT NULL,
        question_text VARCHAR(255) NOT NULL,
        quiz_id VARCHAR(255) NOT NULL,
        CONSTRAINT fk_quiz FOREIGN KEY (quiz_id) REFERENCES quiz_app_service.quiz(quiz_id)
    );

    -- Option table creation
    CREATE TABLE IF NOT EXISTS quiz_app_service.options (
        option_id VARCHAR(255) PRIMARY KEY NOT NULL,
        option_text VARCHAR(255) NOT NULL,
        question_id VARCHAR(255) NOT NULL,
        is_correct boolean NOT NULL,
        CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES quiz_app_service.question(question_id)
    );

END;
$$;
COMMIT TRANSACTION;
