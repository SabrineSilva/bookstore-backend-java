package bookstore.back.validation.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.validation.RentalValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RentalValidationImpl implements RentalValidation {

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public void validateForCreate(RentalEntity rental) {
        List<String> errors = new ArrayList<>();

        validationDeadLine(rental.getDeadline(), errors);
        validateDuplicateRental(rental.getUser().getId(), rental.getBook().getId(), errors);
        validationBook(rental.getBook(), errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }
    }

    @Override
    public void validateUpdate(RentalEntity rental, LocalDate existingDeadline) {
        validateDeadlineUpdate(rental.getDeadline(), existingDeadline);
    }

    private void validationBook(BookEntity entity, List<String> errors) {
        List<RentalEntity> currentRentals = rentalRepository.findAllByBookIdAndReturnDateIsNull(entity.getId());
        int totalRentedNow = currentRentals.size();
        entity.setAvailableQuantity(entity.getTotalQuantity() - totalRentedNow);
        Integer availableQuantity = entity.getAvailableQuantity();

        if (availableQuantity < 1) {
            errors.add("O livro não possui exemplares disponíveis.");
        }
    }

    private void validateDeadlineUpdate(LocalDate newDeadline, LocalDate existingDeadline) {
        if (newDeadline.isBefore(existingDeadline)) {
            throw new BusinessException("A data de previsão deve ser maior que a data de previsão existente.");
        }
    }

    private void validationDeadLine(LocalDate deadLine, List<String> errors) {
        if (deadLine == null) {
            errors.add("A data de previsão de devolução não pode estar vazia.");
        } else {
            LocalDate today = LocalDate.now();
            if (deadLine.isBefore(today)) {
                errors.add("A data de previsão de devolução deve ser depois do dia atual.");
            }
        }
    }

    public void validateDuplicateRental(Integer userId, Integer bookId, List<String> errors) {
        if (rentalRepository.existsByUserIdAndBookIdAndReturnDateIsNull(userId, bookId)) {
            errors.add("O usuário já alugou este livro e ainda não o devolveu.");
        }
    }
}
