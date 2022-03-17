create table image_model
(
    id          serial primary key,
    image_bytes bytea,
    name        varchar(255) not null,
    recipe_id   int,
    user_id     int
);




