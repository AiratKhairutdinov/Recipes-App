create table recipe_directions
(
    recipe_id int not null references recipe(id),
    directions varchar(255)
);