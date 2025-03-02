-- schema.sql nebo genesisresources.dump.sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    person_id VARCHAR(50) UNIQUE NOT NULL,
    uuid VARCHAR(36) UNIQUE NOT NULL
);


INSERT INTO users (name, surname, person_id, uuid) VALUES
('John', 'Doe', 'P001', '550e8400-e29b-41d4-a716-446655440000'),
('Jane', 'Smith', 'P002', '6ba7b810-9dad-11d1-80b4-00c04fd430c8'),
('Alice', 'Johnson', 'P003', '7c9f416b-8e76-11e9-8f48-0cc47a792c0a');