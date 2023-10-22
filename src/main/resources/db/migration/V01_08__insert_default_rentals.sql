INSERT INTO rentals_tb (
    created_at, updated_at, rental_date, deadline, return_date, status, book_id, user_id
)
VALUES
    (now(), now(), '2023-10-10', '2023-10-15', '2023-10-09', 'RETURNED_ON_TIME', 1, 1),
    (now(), now(), '2023-10-11', '2023-10-17', '2023-10-14', 'RETURNED_ON_TIME', 2, 2),
    (now(), now(), '2023-10-12', '2023-10-18', '2023-10-19', 'RETURNED_LATE', 3, 3),
    (now(), now(), '2023-10-13', '2023-10-19', '2023-10-20', 'RETURNED_LATE', 4, 4),
    (now(), now(), '2023-10-14', '2023-10-20', '2023-10-15', 'RETURNED_ON_TIME', 5, 5),
    (now(), now(), '2023-10-15', '2023-10-21', '2023-10-15', 'RETURNED_ON_TIME', 6, 6),
    (now(), now(), '2023-10-16', '2023-10-22', '2023-10-23', 'RETURNED_LATE', 7, 7),
    (now(), now(), '2023-10-17', '2023-10-23', '2023-10-21', 'RETURNED_ON_TIME', 8, 8),
    (now(), now(), '2023-10-18', '2023-10-24', '2023-10-25', 'RETURNED_LATE', 9, 9),
    (now(), now(), '2023-10-19', '2023-10-25', '2023-10-22', 'RETURNED_ON_TIME', 10, 10);
