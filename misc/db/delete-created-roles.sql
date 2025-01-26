BEGIN TRANSACTION;
DO $$ BEGIN

    -- Revoke privileges granted to the roles
    REVOKE ALL PRIVILEGES ON DATABASE quizappservice FROM quiz_app_service_admin_role;
    REVOKE CONNECT ON DATABASE quizappservice FROM quiz_app_service_app_role;

    -- Drop the roles if they exist
    IF EXISTS (SELECT * FROM pg_roles WHERE rolname = 'quiz_app_service_admin_role') THEN
        DROP ROLE quiz_app_service_admin_role;
    END IF;

    IF EXISTS (SELECT * FROM pg_roles WHERE rolname = 'quiz_app_service_app_role') THEN
        DROP ROLE quiz_app_service_app_role;
    END IF;

    -- Restore the original PUBLIC privileges
    GRANT CREATE ON SCHEMA public TO PUBLIC;
    GRANT ALL ON DATABASE quizappservice TO PUBLIC;

END $$ ;
COMMIT TRANSACTION;
