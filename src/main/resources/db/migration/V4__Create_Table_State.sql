create table state (
    id serial primary key,
    name varchar(255) not null,
    initials varchar(2) not null,
    country_id integer not null,
    CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES country(id)
);