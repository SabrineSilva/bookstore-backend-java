package bookstore.back.repositories;

import bookstore.back.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT COUNT(r) FROM RentalEntity r WHERE r.book = :book")
    Long countRentalsByBook(@Param("book") BookEntity book);

    List<Optional<BookEntity>> findByPublisherId(Long id);

    Optional <BookEntity> findByIdAndIsDeletedFalse(Long id);

    Optional <BookEntity> findByIdAndIsDeletedTrue(Long id);

    Optional<BookEntity> findBookByNameAndAuthor(String name, String author);

    List<BookEntity> findAllByIsDeletedFalse();

    List<BookEntity> findAllByIsDeletedTrue();

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findByName(String name);





}
