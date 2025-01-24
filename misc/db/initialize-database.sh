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

psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" -f 001-roles.sql;
#psql -v ON_ERROR_STOP="on" -h "${HOST}" -p "${PORT}" -d "${DATABASE}" -U "${MASTER_USERNAME}" -f 002-service-schema.sql;