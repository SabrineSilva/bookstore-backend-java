package bookstore.back.services;

import bookstore.back.entities.RentalEntity;
import bookstore.back.io.rental.RentalCreateRequest;
import bookstore.back.io.rental.RentalDevolution;
import bookstore.back.io.rental.RentalResponseRequest;
import bookstore.back.io.rental.RentalUpdateRequest;

import java.util.List;

public interface RentalService {
    void create(RentalCreateRequest request);

    void increaseDeadline(RentalUpdateRequest request);

    void returnBook(RentalDevolution request);

    List<RentalResponseRequest> getAll();

    List<RentalResponseRequest> getReturned();

    List<RentalResponseRequest> getPending();

    RentalEntity findById(Integer id);
}
