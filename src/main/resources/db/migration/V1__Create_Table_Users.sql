CREATE TABLE public.users (
    id serial PRIMARY KEY,
    name character varying(255),
    email character varying(255) UNIQUE,
    password character varying(255)
);