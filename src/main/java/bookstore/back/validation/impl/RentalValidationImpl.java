package bookstore.back.validation.impl;

import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.validation.RentalValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentalValidationImpl implements RentalValidation {
    @Override
    public void validate(RentalEntity rental) {
        validationDeadLine(rental.getDeadLine());
    }

    private void validationDeadLine(LocalDate dueDate) {
        LocalDate today = LocalDate.now();

        if (dueDate.isBefore(today)){
            throw new BusinessException("A data de previsão de devolução deve ser depois do dia atual.");
        }
    }
}
