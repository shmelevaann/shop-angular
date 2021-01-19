create table products (
    id bigserial primary key,
    title varchar(255),
    price int,
    created_at date,
    updated_at date);

insert into products (title, price) values
    ('Apple', 10),
    ('Pear', 15),
    ('Banana', 20),
    ('Orange', 25),
    ('Peach', 30),
    ('Pineapple', 40),
    ('Cherry', 20),
    ('Strawberry', 15),
    ('Raspberry', 20);

create table users (
    id bigserial primary key,
    name varchar(255));

insert into users (name) values
    ('User1'),
    ('User2'),
    ('User3');

create table cart_items (
    user_id bigserial,
    product_id bigserial,
    quantity int,
    primary key (user_id, product_id));