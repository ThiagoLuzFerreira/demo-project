CREATE TABLE IF NOT EXISTS "person" (
    "id" UUID NOT NULL,
    "first_name" VARCHAR(255) NULL DEFAULT NULL,
    "last_name" VARCHAR(255) NULL DEFAULT NULL,
    "email" VARCHAR(255) NULL DEFAULT NULL,
    "gender" VARCHAR(255) NULL DEFAULT NULL,
    "cep" VARCHAR(255) NOT NULL,
    "creation_date" TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "uk_fwmwi44u55bo4rvwsv0cln012" UNIQUE ("email")
    );