CREATE DATABASE bookrating ENCODING 'UTF8';
\c bookrating;

CREATE TABLE schema_migrations(version bigint not null primary key, dirty boolean not null);