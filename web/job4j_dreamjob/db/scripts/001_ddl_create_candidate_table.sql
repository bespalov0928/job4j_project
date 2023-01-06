
CREATE TABLE if not exists candidate
(
    id           SERIAL PRIMARY KEY,
    title        TEXT,
    description  TEXT,
    creationDate timestamp,
    visible      boolean,
    cityId       int,
    nameFile     TEXT,
    photo        bytea
);