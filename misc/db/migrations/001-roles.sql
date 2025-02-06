BEGIN TRANSACTION;

DO $$
BEGIN
    -- Revoke privileges from the "PUBLIC" role.
    REVOKE CREATE ON SCHEMA public FROM PUBLIC;
    REVOKE ALL ON DATABASE quizappservice FROM PUBLIC;

    -- Create and grant all privileges to the Admin role.
    -- The Admin role will have all privileges on the 'quizappservice' database.
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'quiz_app_service_admin_role') THEN
        CREATE ROLE quiz_app_service_admin_role;
    END IF;
    GRANT ALL PRIVILEGES ON DATABASE quizappservice TO quiz_app_service_admin_role;

    -- Create and grant connect privileges to the App role.
    -- The App role will only have read/write permissions on the 'quizappservice' database.
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'quiz_app_service_app_role') THEN
        CREATE ROLE quiz_app_service_app_role;
    END IF;
    GRANT CONNECT ON DATABASE quizappservice TO quiz_app_service_app_role;

END $$;

COMMIT TRANSACTION;
