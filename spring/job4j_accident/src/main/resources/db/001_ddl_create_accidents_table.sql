CREATE TABLE if not exists accidents
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR (255),
    text        VARCHAR (255),
    address     VARCHAR (255),
    accident_id integer
);