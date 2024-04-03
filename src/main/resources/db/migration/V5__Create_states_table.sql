CREATE TABLE states (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    country_id INT NOT NULL,

    FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE INDEX idx_states_name ON states(name);