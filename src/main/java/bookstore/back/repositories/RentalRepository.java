package bookstore.back.repositories;

import bookstore.back.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Integer> {
    List<Optional<RentalEntity>> findByBookId(Integer id);

    List<Optional<RentalEntity>> findByUserId(Integer id);

    List<RentalEntity> findAllByBookIdAndReturnDateIsNull(Integer id);


    List<RentalEntity> findAllByReturnDateIsNotNull();

    List<RentalEntity> findAllByReturnDateIsNull();

    Optional<RentalEntity> findByIdAndReturnDateIsNull(Integer id);

    boolean existsByUserIdAndBookIdAndReturnDateIsNull(Integer userId, Integer bookId);

    Integer countByUserId(Integer id);
}
