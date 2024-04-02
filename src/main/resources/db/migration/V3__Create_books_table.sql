CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    summary VARCHAR(500) NOT NULL,
    table_of_contents TEXT,
    price DECIMAL(5,2) NOT NULL,
    pages_number INT NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    publication_date DATE NOT NULL,

    author_id INT NOT NULL,
    category_id INT NOT NULL,

    FOREIGN KEY (author_id) REFERENCES authors(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_isbn ON books(isbn);