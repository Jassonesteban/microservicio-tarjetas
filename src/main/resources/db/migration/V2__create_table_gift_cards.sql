CREATE TABLE gift_cards (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255),
    amount DECIMAL(10, 2),
    name VARCHAR(255),
    description TEXT,
    company VARCHAR(255),
    created_at TIMESTAMP,
    expires_at TIMESTAMP
);
