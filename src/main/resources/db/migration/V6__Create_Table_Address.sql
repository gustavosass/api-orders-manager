create table address(
    id serial primary key,
    street varchar(255),
    number varchar(255),
    district varchar(255) ,
    complement varchar(255),
    postal_code varchar(255),
    city_id integer,
    CONSTRAINT fk_city FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);