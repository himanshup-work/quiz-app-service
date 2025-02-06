-- Set variables for admin and app user passwords (to be passed externally)
SET vars.new_admin_password TO :new_admin_password;
SET vars.new_user_password TO :new_user_password;

BEGIN TRANSACTION;

-- Create Admin User and Assign Role
DO $$
DECLARE
    new_admin_password text = current_setting('vars.new_admin_password');
BEGIN
    -- Check if the admin user already exists
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'quiz_app_service_admin') THEN
        -- Create the admin user with the provided password
        EXECUTE format('CREATE USER quiz_app_service_admin WITH PASSWORD %L;', new_admin_password);
        -- Grant the quiz_app_service_admin_role to the admin user
        GRANT quiz_app_service_admin_role TO quiz_app_service_admin;
    END IF;
EXCEPTION WHEN OTHERS THEN
    -- If an error occurs, log the message
    RAISE NOTICE 'Error occurred while creating admin user: %', SQLERRM;
END $$;

-- Create App User and Assign Role
DO $$
DECLARE
    new_user_password text = current_setting('vars.new_user_password');
BEGIN
    -- Check if the app user already exists
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'quiz_app_service_user') THEN
        -- Create the app user with the provided password
        EXECUTE format('CREATE USER quiz_app_service_user WITH PASSWORD %L;', new_user_password);
        -- Grant the quiz_app_service_app_role to the app user
        GRANT quiz_app_service_app_role TO quiz_app_service_user;
    END IF;
EXCEPTION WHEN OTHERS THEN
    -- If an error occurs, log the message
    RAISE NOTICE 'Error occurred while creating app user: %', SQLERRM;
END $$;

COMMIT TRANSACTION;
