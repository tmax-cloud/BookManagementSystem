CREATE DATABASE bookratingv2 ENCODING 'UTF8';
\c bookratingv2;

CREATE TABLE schema_migrations(version bigint not null primary key, dirty boolean not null);