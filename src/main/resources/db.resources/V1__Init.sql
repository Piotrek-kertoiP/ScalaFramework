CREATE TABLE IF NOT EXISTS customers (
    id uuid PRIMARY KEY,
    orgId uuid NOT NULL,
    firstName text NOT NULL,
    lastName text NOT NULL,
    pesel number NOT NULL,
    UNIQUE(pesel)
);
