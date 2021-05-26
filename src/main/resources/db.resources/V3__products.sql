CREATE TABLE IF NOT EXISTS products (
    id uuid PRIMARY KEY,
    orgId uuid NOT NULL,
    name text NOT NULL,
    price number NOT NULL
);
