package bookstore.back.repositories;

import bookstore.back.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    List<Optional<BookEntity>> findByPublisherId(Integer id);

    Optional<BookEntity> findByIdAndIsDeletedFalse(Integer id);

    Optional<BookEntity> findByIdAndIsDeletedTrue(Integer id);

    Optional<BookEntity> findBookByNameAndAuthorAndPublisherIdAndIsDeletedFalse(String name, String author, Integer publisherId);

    List<BookEntity> findAllByIsDeletedFalse();

    List<BookEntity> findAllByIsDeletedTrue();

    List<BookEntity> findByNameAndAuthorAndPublisherIdAndIsDeletedFalse(String name, String author, Integer publisherId);
}
