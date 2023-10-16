CREATE TABLE rentals_tb (
    id SERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    rental_date DATE,
    deadline DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20),
    book_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT rentals_tb_pk PRIMARY KEY (id),
    FOREIGN KEY (book_id) REFERENCES books_tb(id),
    FOREIGN KEY (user_id) REFERENCES users_tb(id)
);
