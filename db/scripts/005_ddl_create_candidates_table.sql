create table candidates
(
    id            serial primary key,
    title         varchar not null,
    description   varchar not null,
    creation_date timestamp,
    visible       boolean not null,
    file_id       int references files(id)
);
