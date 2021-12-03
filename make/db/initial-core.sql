CREATE DATABASE bookcore ENCODING 'UTF8';
\c bookcore;

CREATE TABLE schema_migrations(version bigint not null primary key, dirty boolean not null);