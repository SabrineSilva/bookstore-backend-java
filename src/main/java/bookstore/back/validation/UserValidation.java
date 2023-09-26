package bookstore.back.validation;

import bookstore.back.entities.UserEntity;

public interface UserValidation {

    void validate(UserEntity user);

    void validateUpdate(UserEntity user);

    void validateForDelete(Long id);
}
