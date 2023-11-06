package bookstore.back.repositories;

import bookstore.back.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserByEmailAndIsDeletedFalse(String email);

    Optional<UserEntity> findByIdAndIsDeletedTrue(Integer id);

    Optional<UserEntity> findByIdAndIsDeletedFalse(Integer id);

    List<UserEntity> findAllByIsDeletedFalse();

    List<UserEntity> findAllByIsDeletedTrue();

    List<UserEntity> findAllByEmailAndIsDeletedFalse(String email);

}


