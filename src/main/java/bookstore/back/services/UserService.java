package bookstore.back.services;

import bookstore.back.entities.UserEntity;
import bookstore.back.io.user.UserCreateRequest;
import bookstore.back.io.user.UserResponseRequest;
import bookstore.back.io.user.UserUpdateRequest;

import java.util.List;

public interface UserService {
    void create(UserCreateRequest request);

    UserEntity findById(Long id);

    List<UserResponseRequest> getAll();
    void update(UserUpdateRequest request);

    void delete(Long id);
}
