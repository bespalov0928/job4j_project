create table if not exists persons (
    id serial primary key,
    username varchar(50) not null,
    password varchar(100) not null
)