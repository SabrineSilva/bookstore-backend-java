package bookstore.back.io.rental;

import bookstore.back.entities.RentalEntity;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    public RentalResponseRequest toRentalResponseRequest(RentalEntity rental){
        RentalResponseRequest response = new RentalResponseRequest();
        response.setId(rental.getId());
        response.setRentalDate(rental.getRentalDate());
        response.setDueDate(rental.getDeadLine());
        response.setReturnDate(rental.getReturnDate());
        response.setStatus(rental.getStatus());
        response.setBookName(rental.getBook().getName());
        response.setUserName(rental.getUser().getName());
        return response;
    }
}
