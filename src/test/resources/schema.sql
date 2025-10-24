CREATE TABLE IF NOT EXISTS test_entity (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024),
    procure_date DATE,
    create_timestamp TIMESTAMP(3),
    price DECIMAL(19,4),
    status VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    category VARCHAR(50) NOT NULL,
    rating INT NOT NULL
);

INSERT INTO test_entity (id, name, description, procure_date, create_timestamp, price, status, active, category, rating) VALUES
('49fc5e08-8a05-4fbc-92bf-f5333453bab0', 'Gaming Laptop', 'High-end gaming', '2025-01-15', '2024-01-15 10:30:00.000', 1299.99, 'ACTIVE', true, 'Electronics', 5),
('a04cfd25-2e35-45fd-abe0-9d7eea4c1a87', 'Office Laptop', 'Business laptop', '2025-02-10', '2024-01-20 09:15:00.000', 899.99, 'ACTIVE', true, 'Electronics', 4),
('a1b64b2d-ab4d-4080-8f19-0aeca3472638', 'Budget Laptop', 'Entry-level', '2025-03-05', '2024-02-01 14:45:00.000', 499.99, 'NEW', true, 'Electronics', 3),
('7db9a915-6dfc-45e6-9d9d-a349d4cdcc6c', 'Standard Laptop', 'Gaming', '2025-01-15', '2024-01-15 10:30:00.000', 999.99, 'ACTIVE', true, 'Electronics', 5),
('92a582b1-961f-43b1-ae53-8a40d710b8b6', 'Smartphone', 'Latest', '2025-02-28', '2024-02-15 16:30:00.000', 699.99, 'DELETED', true, 'Electronics', 2),
('bc410d12-07aa-48af-8eee-76ab2b85f49c', 'Budget Phone', 'Budget', '2025-03-05', '2024-02-01 14:45:00.000', 499.99, 'NEW', true, 'Electronics', 3),
('03162b2f-50e2-43cf-ac1b-5eab4b83b170', 'Tablet Pro', 'Pro', '2025-01-20', '2024-01-25 11:00:00.000', 799.99, 'ACTIVE', false, 'Electronics', 4),
('b37955c8-032b-477c-b621-984c9923ef16', 'Basic Phone', 'Budget', '2025-02-28', '2024-02-15 16:30:00.000', 699.99, 'ACTIVE', true, 'Electronics', 2),
('9c488e54-8449-45f8-bed2-979807755ad5', 'Premium Laptop', 'Gaming', '2025-01-15', '2024-01-15 10:30:00.000', 999.99, 'ACTIVE', true, 'Electronics', 5),
('3d950bfc-0323-42dc-9a28-8ccad7486501', 'Simple Phone', NULL, '2025-02-28', '2024-02-15 16:30:00.000', 699.99, 'ACTIVE', true, 'Electronics', 2);