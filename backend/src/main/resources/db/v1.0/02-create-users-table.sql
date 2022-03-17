create table users
(
    id           serial primary key,
    username     varchar(255) unique,
    email        varchar(255) unique,
    password     varchar(255),
    firstname    varchar(255) not null,
    lastname     varchar(255) not null,
    role varchar(32),
    bio          varchar(255),
    created_date timestamp
);