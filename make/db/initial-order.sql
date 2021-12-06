CREATE DATABASE bookorder ENCODING 'UTF8';
\c bookorder;

CREATE TABLE schema_migrations(version bigint not null primary key, dirty boolean not null);