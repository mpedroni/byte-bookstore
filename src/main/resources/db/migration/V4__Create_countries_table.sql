CREATE TABLE countries (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE INDEX idx_countries_name ON countries (name);