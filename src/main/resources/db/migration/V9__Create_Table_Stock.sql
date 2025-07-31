CREATE TABLE stocks (
    id serial primary key,
    initial_quantity DECIMAL(15,4) NOT NULL,
    current_quantity DECIMAL(15,4) NOT NULL,
    cost_price DECIMAL(15,4) NOT NULL,
    entry_date DATE NOT NULL,
    item_id integer NOT NULL,
    CONSTRAINT FK_ESTOQUE_ITEM FOREIGN KEY (item_id) REFERENCES item(id)
);