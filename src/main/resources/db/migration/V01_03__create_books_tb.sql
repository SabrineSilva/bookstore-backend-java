CREATE TABLE books_tb (
    id SERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    launch_date INTEGER NOT NULL,
    total_quantity INTEGER NOT NULL,
    available_quantity INTEGER,
    total_times_rented INTEGER,
    publisher_id INTEGER NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    CONSTRAINT books_tb_pk PRIMARY KEY (id),
    CONSTRAINT publishers_tb_fk FOREIGN KEY (publisher_id) REFERENCES publishers_tb(id)
);
