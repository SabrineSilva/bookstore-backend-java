package bookstore.back.services;

import bookstore.back.entities.BookEntity;
import bookstore.back.io.book.BookCreateRequest;
import bookstore.back.io.book.BookResponseDeletedRequest;
import bookstore.back.io.book.BookResponseRequest;
import bookstore.back.io.book.BookUpdateRequest;

import java.util.List;


public interface BookService {
    void create(BookCreateRequest request);

    void update(BookUpdateRequest request);

    List<BookResponseRequest> getAll();

    List<BookResponseDeletedRequest> getAllDeleted();

    BookEntity findById(Integer id);

    BookEntity findByDeleteId(Integer id);

    void delete(Integer id);

    void deletePermanently(Integer id);


}
