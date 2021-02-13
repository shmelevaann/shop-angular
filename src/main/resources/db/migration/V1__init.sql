create table products (
    id bigserial primary key,
    title varchar(255),
    price int,
    created_at date,
    updated_at date);

create table users (
    id bigserial primary key,
    username varchar(255) unique,
    password varchar(255),
    email varchar(255),
    created_at date,
    updated_at date
);

create table cart_items (
    user_id bigserial,
    product_id bigserial,
    quantity int,
    primary key (user_id, product_id)
);

create table roles (
    id bigserial primary key,
    name varchar(255),
    created_at date,
    updated_at date
);

create table users_roles (
    user_id bigserial,
    role_id bigserial,
    primary key (user_id, role_id)
);

create table orders (
    id bigserial primary key,
    user_id bigserial,
    address_id bigserial,
    date date
);

create table order_items (
    order_id bigserial,
    product_id bigserial,
    quantity int,
    price int,
    primary key (order_id, product_id)
);

create table addresses (
    id bigserial primary key,
    value varchar(255)
);

create table users_addresses {
    user_id bigserial,
    address_id bigserial,
    primary key (user_id, address_id)
}

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

insert into users (username, password) values
    ('User1', '$2y$12$oru.fPCssUi0yjxsOPmNiOneFqXH4SnMJ9fb5m0pIttzTmZQIpVOK'),
    ('User2', '$2y$12$pnQX7Zl41fTZ9m5EeJoxG.zzY/I8fFybpRc/IstLtMwVD7ek0r/Y.'),
    ('User3', '$2y$12$6MsH3q14BiX6NW77Z.BPBu/WvKja1fZ76nu0JidK2ruXAKTlkb8qW');

insert into roles (name) values
    ('ROLE_USER'),
    ('ROLE_ADMIN');

insert into users_roles (user_id, role_id) values
    (1, 1),
    (2, 2);
