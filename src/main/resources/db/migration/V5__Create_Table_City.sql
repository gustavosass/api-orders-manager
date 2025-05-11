create table city(
    id serial primary key,
    name varchar(255) not null,
    state_id integer not null,
    CONSTRAINT fk_state FOREIGN KEY (state_id) REFERENCES state(id),
    CONSTRAINT unique_city_state UNIQUE (name, state_id)
);