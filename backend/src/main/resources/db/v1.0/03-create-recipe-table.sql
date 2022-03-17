create table recipe
(
    id serial primary key,
    name varchar(255) not null unique,
    description varchar(255),
    cook_time int,
    prep_time int,
    servings int,
    likes int,
    category_id int not null references recipe_category(id),
    user_id int not null references users(id),
    created_date timestamp
);


