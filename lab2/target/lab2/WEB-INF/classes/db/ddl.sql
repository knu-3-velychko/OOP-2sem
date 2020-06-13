CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
            create type user_role AS ENUM ('ADMIN', 'USER');
        END IF;
    END
$$;

CREATE TABLE IF NOT EXISTS accounts
(
    id      SERIAL PRIMARY KEY,
    balance FLOAT
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(20),
    surname  VARCHAR(20),
    password VARCHAR(20)        NOT NULL,
    role     user_role          NOT NULL DEFAULT 'USER',
    username VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS cards
(
    cardname   VARCHAR(20) UNIQUE NOT NULL,
    blocked    BOOLEAN            NOT NULL DEFAULT false,
    client_id  INT REFERENCES users (id),
    account_id INT REFERENCES accounts (id)
);

CREATE TABLE IF NOT EXISTS payments
(
    id         SERIAL PRIMARY KEY,
    account_id INT REFERENCES accounts (id),
    pay        FLOAT,
    comment    TEXT
);

insert into users (name, surname, password, role, username)
Select 'admin', 'admin', 'admin', 'ADMIN', 'admin'
Where not exists(select * from users where username = 'admin' and role = 'ADMIN');
