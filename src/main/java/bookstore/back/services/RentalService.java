package bookstore.back.services;

import bookstore.back.io.rental.RentalCreateRequest;
import bookstore.back.io.rental.RentalResponseRequest;

import java.util.List;

public interface RentalService {
    void create(RentalCreateRequest request);

    List<RentalResponseRequest> getAll();

    void delete(Long id);
}
