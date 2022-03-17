create table comment
(
    id           serial primary key,
    message      varchar(255) not null,
    username     varchar(255) not null,
    user_id      int not null,
    recipe_id    int not null references recipe (id),
    created_date timestamp
);



