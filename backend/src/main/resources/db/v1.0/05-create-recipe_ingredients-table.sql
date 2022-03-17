create table recipe_ingredients
(
    recipe_id int not null references recipe(id),
    ingredients varchar(255)
);