create table budget(
    id serial PRIMARY KEY,
    number VARCHAR(200) NOT NULL,
    budget_date DATE NOT NULL,
    approved_date DATE,
    cancelled_date DATE,
    status VARCHAR(50) NOT NULL,
    total_value DECIMAL(15,4),
    customer_id integer NOT NULL,
    CONSTRAINT FK_BUDGET_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer(id)
);


