package bookstore.back.validation.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.validation.RentalValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RentalValidationImpl implements RentalValidation {

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public void validateForCreate(RentalEntity rental) {
        validationDeadLine(rental.getDeadLine());
        validateDuplicateRental(rental.getUser().getId(), rental.getBook().getId());
        validationBook(rental.getBook());
    }

    private void validationBook(BookEntity entity) {
        List<RentalEntity> currentRentals = rentalRepository.findAllByBookIdAndReturnDateIsNull(entity.getId());
        int totalRentedNow = currentRentals.size();
        entity.setAvailableQuantity(entity.getTotalQuantity() - totalRentedNow);
        Integer availableQuantity = entity.getAvailableQuantity();


        if (availableQuantity < 1){

            throw new BusinessException("O livro não possui exemplares disponíveis!");
        }
    }

    @Override
    public void validateUpdate(RentalEntity rental) {
        validateDuplicateRental(rental.getUser().getId(), rental.getBook().getId());
        validationDeadLineUpdate(rental.getRentalDate(), rental.getDeadLine());

    }




    private void validationDeadLineUpdate(LocalDate rentalDate, LocalDate deadLine) {
        LocalDate today = LocalDate.now();

        if (deadLine.isBefore(rentalDate)  || deadLine.isBefore(today) ) {
            throw new BusinessException("A data de previsão de devolução deve ser depois ou igual ao dia atual e depois da data que foi realizado o aluguel.");
        }
    }



    private void validationDeadLine(LocalDate deadLine) {
        if (deadLine == null) {
            throw new BusinessException("A data de previsão de devolução não pode estar vazia.");
        }

        LocalDate today = LocalDate.now();

        if (deadLine.isBefore(today)){
            throw new BusinessException("A data de previsão de devolução deve ser depois do dia atual.");
        }
    }

    public void validateDuplicateRental(Long userId, Long bookId) {
        if (rentalRepository.existsByUserIdAndBookIdAndReturnDateIsNull(userId, bookId)) {
            throw new BusinessException("O usuário já alugou este livro e ainda não o devolveu.");
        }
    }
}
