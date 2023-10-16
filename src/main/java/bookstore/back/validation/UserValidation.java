package bookstore.back.validation;

import bookstore.back.entities.UserEntity;

public interface UserValidation {

    void validateForCreate(UserEntity user);

    void validateUpdate(UserEntity user);

    void validateForDelete(Integer id);
}
