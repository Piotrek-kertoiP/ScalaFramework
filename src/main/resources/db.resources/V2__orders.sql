CREATE TABLE IF NOT EXISTS orders (
    id uuid PRIMARY KEY,
    orgId uuid NOT NULL,
    customerId uuid NOT NULL,
    productId uuid NOT NULL,
    quantity number NOT NULL,
    price number NOT NULL,
);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_productId
    FOREIGN KEY (productId)
    REFERENCES products (id);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_customerId
    FOREIGN KEY (customerId)
    REFERENCES customers (id);
