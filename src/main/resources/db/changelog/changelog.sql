-- liquibase formatted sql

-- changeset liquibase:1
create table users
(
    id         integer generated by default as identity primary key,
    username   varchar(100) not null,
    email      varchar(100) not null,
    phone      varchar(20),
    password   varchar      not null,
    access     varchar(10)  not null,
    visibility varchar(10)  not null,
    is_active  boolean      not null,
    img_url    text
);

create table collection_types
(
    id         integer generated by default as identity primary key,
    user_id    integer     not null references users,
    name       text        not null,
    visibility varchar(10) not null
);

create table collections
(
    id              integer generated by default as identity primary key,
    user_id         integer       not null references users,
    collection_type integer       not null references collection_types,
    name            text,
    description     text,
    visibility      varchar(10)   not null,
    likes           int default 0 not null,
    img_url         text
);

create table items
(
    id            integer generated by default as identity primary key,
    collection_id integer       not null references collections,
    name          text,
    description   text,
    visibility    varchar(10)   not null,
    likes         int default 0 not null,
    is_active     boolean       not null
);

create table items_images
(
    id      integer generated by default as identity primary key,
    item_id integer not null references items,
    img_url text
);

create table features
(
    id                 integer generated by default as identity primary key,
    collection_type_id integer     not null references collection_types,
    name               text        not null,
    data_type          varchar(10) not null
);

create table item_features
(
    id         integer generated by default as identity primary key,
    item_id    integer not null references items,
    feature_id integer not null references features,
    data       text    not null
);