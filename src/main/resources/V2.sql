create table users (
    id SERIAL primary key,
    name varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255),
    phone_number varchar(255),
    role varchar(255) not null
);