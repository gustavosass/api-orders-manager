create table client(
    id serial primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255),
    birth_date date,
    document varchar(255) not null,
    address varchar(255),
    street varchar(255),
    number varchar(255),
    district varchar(255),
    complement varchar(255),
    postal_code varchar(255),
    address_id integer,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id)
);