create table recipe_users_liked
(
    recipe_id int not null references recipe(id),
    users_liked varchar(255)
);