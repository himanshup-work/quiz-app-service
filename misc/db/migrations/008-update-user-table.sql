BEGIN TRANSACTION;

DO $$
BEGIN

    ALTER TABLE quiz_app_service.users
    DROP COLUMN full_name;

    ALTER TABLE quiz_app_service.users
    ADD COLUMN first_name VARCHAR(255) NOT NUll,
    ADD COLUMN last_name VARCHAR(255) NOT NUll,
    ADD COLUMN bio TEXT NUll;

END;
$$;
COMMIT TRANSACTION;
