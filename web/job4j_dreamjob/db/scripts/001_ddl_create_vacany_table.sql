CREATE TABLE if not exists vacancy
(
    id           SERIAL PRIMARY KEY,
    title        TEXT,
    description  TEXT,
    creationDate timestamp,
    visible      boolean,
    cityId       int
);