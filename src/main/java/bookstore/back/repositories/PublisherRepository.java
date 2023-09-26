package bookstore.back.repositories;

import bookstore.back.entities.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Long>{
    Optional<PublisherEntity> findPublisherByName(String name);

    List<PublisherEntity> findAllByIsDeletedFalse();

    List<PublisherEntity> findAllByIsDeletedTrue();

    Optional<PublisherEntity> findByIdAndIsDeletedFalse(Long id);

    Optional<PublisherEntity> findByIdAndIsDeletedTrue(Long id);


}
