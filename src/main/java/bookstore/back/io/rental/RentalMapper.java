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
        response.setDeadline(rental.getDeadline());
        response.setReturnDate(rental.getReturnDate());
        response.setBookName(rental.getBook().getName());
        response.setUserName(rental.getUser().getName());
        response.setStatus(getStatus(rental.getDeadline(), rental.getReturnDate()));
        return response;
    }

    public RentalDetailRequest toRentalDetailRequest(RentalEntity rental){
        RentalDetailRequest response = new RentalDetailRequest();
        response.setId(rental.getId());
        response.setRentalDate(rental.getRentalDate());
        response.setDeadline(rental.getDeadline());
        response.setReturnDate(rental.getReturnDate());
        response.setBookName(rental.getBook().getName());
        response.setUserName(rental.getUser().getName());
        response.setStatus(getStatus(rental.getDeadline(), rental.getReturnDate()));
        response.setCreatedAt(rental.getCreatedAt());
        response.setUpdatedAt(rental.getUpdatedAt());
        return response;
    }

    private Status getStatus(LocalDate deadline, LocalDate returnDate) {
        LocalDate today = LocalDate.now();
        if (returnDate == null && today.isBefore(deadline) || returnDate == null && today.isEqual(deadline)) {
            return Status.PENDING_ON_TIME;
        } else if (returnDate == null && today.isAfter(deadline)) {
            return Status.PENDING_LATE;
        } else if (returnDate != null && today.isBefore(deadline) || returnDate != null && today.isEqual(deadline)) {
            return Status.RETURNED_ON_TIME;
        } else {
            return Status.RETURNED_LATE;
        }
    }
}

