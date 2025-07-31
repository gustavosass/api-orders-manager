create table city(
    id serial primary key,
    name varchar(255) not null,
    state_id integer not null,
    CONSTRAINT FK_CITY_STATE FOREIGN KEY (state_id) REFERENCES state(id),
    CONSTRAINT unique_city_state UNIQUE (name, state_id)
);