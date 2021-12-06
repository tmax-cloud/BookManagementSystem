CREATE DATABASE bookpayment ENCODING 'UTF8';
\c bookpayment;

CREATE TABLE schema_migrations(version bigint not null primary key, dirty boolean not null);