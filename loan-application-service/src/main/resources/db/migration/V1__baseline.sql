CREATE SCHEMA IF NOT EXISTS pennybank_loan_application_store;

CREATE TABLE pennybank_loan_application_store.loan_application
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL PRIMARY KEY,
    customer_id BIGINT                                                 NOT NULL,
    amount      DECIMAL                                                NOT NULL,
    duration    INT                                                    NOT NULL,
    status      VARCHAR(10)                                            NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE                               NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE
);

CREATE INDEX customer_id_index ON pennybank_loan_application_store.loan_application (customer_id);