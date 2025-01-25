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
        image BYTEA
    );

    -- Quiz table creation
    CREATE TABLE IF NOT EXISTS quiz_app_service.quiz (
        quiz_id VARCHAR(255) PRIMARY KEY NOT NULL,
        quiz_name VARCHAR(255) NOT NULL,
        user_id VARCHAR(255) NOT NULL,
        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES quiz_app_service.users(user_id)
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
        CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES quiz_app_service.question(question_id)
    );
END;
$$;

COMMIT TRANSACTION;
