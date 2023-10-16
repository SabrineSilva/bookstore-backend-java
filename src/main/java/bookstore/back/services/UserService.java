package bookstore.back.services;

import bookstore.back.entities.UserEntity;
import bookstore.back.io.user.UserCreateRequest;
import bookstore.back.io.user.UserResponseRequest;
import bookstore.back.io.user.UserUpdateRequest;

import java.util.List;

public interface UserService {
    void create(UserCreateRequest request);

    UserEntity findById(Integer id);

    UserEntity findByDeletedId(Integer id);

    List<UserResponseRequest> getAll();

    List<UserResponseRequest> getAllDeleted();

    void update(UserUpdateRequest request);

    void delete(Integer id);

    void permanentlyDelete(Integer id);
}
