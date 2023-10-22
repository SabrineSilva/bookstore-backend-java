package bookstore.back.validation;

import bookstore.back.entities.RentalEntity;

import java.time.LocalDate;

public interface RentalValidation {
    void validateForCreate(RentalEntity entity);

    void validateUpdate(RentalEntity rental, LocalDate existingDeadline);
}
