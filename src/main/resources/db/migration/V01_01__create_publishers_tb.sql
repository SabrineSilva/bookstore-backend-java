CREATE TABLE publishers_tb (
    id SERIAL NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL,
    name VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    CONSTRAINT publishers_tb_pk PRIMARY KEY (id)
);