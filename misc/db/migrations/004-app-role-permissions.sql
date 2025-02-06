-- Grant usage on the schema to the app role
GRANT USAGE
    ON SCHEMA quiz_app_service
    TO quiz_app_service_app_role;

-- Grant SELECT, INSERT, UPDATE, DELETE on all tables in the schema to the app role
GRANT SELECT, INSERT, UPDATE, DELETE
    ON ALL TABLES IN SCHEMA quiz_app_service
    TO quiz_app_service_app_role;

-- If the app role needs permissions on sequences for inserting into auto-incrementing fields
GRANT USAGE, SELECT
    ON ALL SEQUENCES IN SCHEMA quiz_app_service
    TO quiz_app_service_app_role;

