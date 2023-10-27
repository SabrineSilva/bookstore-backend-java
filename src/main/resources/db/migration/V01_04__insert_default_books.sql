INSERT INTO books_tb (
    created_at, updated_at, is_deleted, name, author, launch_date, total_quantity, available_quantity, total_times_rented, publisher_id
)
VALUES
    (now(), now(), false, 'O Livro Mais Legal da Tabela', 'Pedro Lucas', 2023, 100, 100, 0, 1),
    (now(), now(), false, 'Segredos do Universo', 'Emily Davis', 2022, 150, 150, 0, 2),
    (now(), now(), false, 'Caminhando pela Floresta', 'Robert Johnson', 2023, 75, 75, 0, 3),
    (now(), now(), false, 'O Mistério do Relógio Quebrado', 'Sarah White', 2020, 120, 120, 0, 4),
    (now(), now(), false, 'Em Busca da Liberdade', 'Michael Turner', 2021, 200, 200, 0, 5),
    (now(), now(), false, 'Amor à Primeira Vista', 'Laura Anderson', 2019, 90, 90, 0, 6),
    (now(), now(), false, 'O Enigma de Atlantis', 'James Parker', 2021, 180, 180, 0, 7),
    (now(), now(), false, 'O Segredo da Longevidade', 'Maria Garcia', 2018, 60, 60, 0, 8),
    (now(), now(), false, 'Código das Estrelas', 'Daniel Brown', 2020, 130, 130, 0, 9),
    (now(), now(), false, 'A Revolta das Máquinas', 'Thomas Johnson', 2023, 110, 110, 0, 10);
