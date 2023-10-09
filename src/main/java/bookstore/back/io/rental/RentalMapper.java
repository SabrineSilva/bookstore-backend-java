package bookstore.back.io.rental;

import bookstore.back.entities.RentalEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentalMapper {

    public RentalResponseRequest toRentalResponseRequest(RentalEntity rental){
        RentalResponseRequest response = new RentalResponseRequest();
        response.setId(rental.getId());
        response.setRentalDate(rental.getRentalDate());
        response.setDeadLine(rental.getDeadLine());
        response.setReturnDate(rental.getReturnDate());
        response.setBookName(rental.getBook().getName());
        response.setUserName(rental.getUser().getName());
        response.setStatus(getStatus(rental.getDeadLine(), rental.getReturnDate()));
        return response;
    }

    public RentalDetailRequest toRentalDetailRequest(RentalEntity rental){
        RentalDetailRequest response = new RentalDetailRequest();
        response.setId(rental.getId());
        response.setRentalDate(rental.getRentalDate());
        response.setDeadLine(rental.getDeadLine());
        response.setReturnDate(rental.getReturnDate());
        response.setBookName(rental.getBook().getName());
        response.setUserName(rental.getUser().getName());
        response.setStatus(getStatus(rental.getDeadLine(), rental.getReturnDate()));
        response.setCreatedAt(rental.getCreatedAt());
        response.setUpdatedAt(rental.getUpdatedAt());
        return response;
    }

    private Status getStatus(LocalDate deadLine, LocalDate returnDate) {
        LocalDate today = LocalDate.now();
        if (returnDate == null && today.isBefore(deadLine) || returnDate == null && today.isEqual(deadLine)) {
            return Status.PENDING_ON_TIME;
        } else if (returnDate == null && today.isAfter(deadLine)) {
            return Status.PENDING_LATE;
        } else if (returnDate != null && today.isBefore(deadLine) || returnDate != null && today.isEqual(deadLine)) {
            return Status.RETURNED_ON_TIME;
        } else {
            return Status.RETURNED_LATE;
        }
    }
}

