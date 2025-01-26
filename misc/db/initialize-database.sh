#!/bin/sh

# Get the passwords from the command line
MASTER_PWD="${1}"
NEW_ADMIN_PWD="${2}"
NEW_USER_PWD="${3}"
HOST="${4}"
PORT="${5}"
DATABASE="quizappservice"
MASTER_USERNAME="postgres"
export PGPASSWORD=${MASTER_PWD}

# Run the first 3 scripts with the master user
psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" -f 001-roles.sql
psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" -f 002-service-schema.sql
psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" -f 003-ingestion-table.sql
psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" -f 004-app-role-permissions.sql

# Now run the user creation script (005-users.sql) with admin and app user passwords
psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" \
    -v new_admin_password="${NEW_ADMIN_PWD}" -v new_user_password="${NEW_USER_PWD}" -f 005-users.sql

