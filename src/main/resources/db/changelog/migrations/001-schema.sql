--create database db;
--GRANT ALL PRIVILEGES ON DATABASE db TO app;
--GRANT ALL PRIVILEGES ON SCHEMA public TO app;
CREATE TABLE USERS (
                           id SERIAL PRIMARY KEY,
                           login VARCHAR(100) UNIQUE,
                           password VARCHAR(100)
);

CREATE INDEX IDX_USERS_LOGINS
    ON USERS(login);

CREATE TABLE FILES (
                        id SERIAL PRIMARY KEY,
                        filename VARCHAR(100) UNIQUE,
                        size INT,
                        user_id INT,
                        CONSTRAINT fk_user
                            FOREIGN KEY(user_id)
                                REFERENCES USERS(id)
);