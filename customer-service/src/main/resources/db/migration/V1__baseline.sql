CREATE SCHEMA IF NOT EXISTS customer_store;

CREATE TABLE customer_store.customer
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL PRIMARY KEY,
    user_id    VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    phone      VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE
);

ALTER TABLE customer_store.customer ADD CONSTRAINT unique_user_id UNIQUE (user_id)