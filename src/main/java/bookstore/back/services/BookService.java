package bookstore.back.services;

import bookstore.back.entities.BookEntity;
import bookstore.back.io.book.BookCreateRequest;
import bookstore.back.io.book.BookResponseRequest;
import bookstore.back.io.book.BookUpdateRequest;

import java.util.List;


public interface BookService {
    void create(BookCreateRequest request);

    void update(BookUpdateRequest request);

    List<BookResponseRequest> getAll();

    List<BookResponseRequest> getAllDeleted();

    BookEntity findById(Long id);

    BookEntity findByDeleteId(Long id);

    void delete(Long id);

    void deletePermanently(Long id);


}
