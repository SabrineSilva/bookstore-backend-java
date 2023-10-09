package bookstore.back.validation;

import bookstore.back.entities.RentalEntity;

public interface RentalValidation {
    void validateForCreate(RentalEntity entity);

    void validateUpdate(RentalEntity entity);
}
