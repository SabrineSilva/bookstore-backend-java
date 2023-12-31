package bookstore.back.repositories;

import bookstore.back.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    List<Optional<RentalEntity>> findByBookId(Long id);

    List<Optional<RentalEntity>> findByUserId(Long id);


}
