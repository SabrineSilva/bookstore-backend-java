package bookstore.back.controllers;

import bookstore.back.io.rental.*;
import bookstore.back.open_api.RentalControllerOpenApi;
import bookstore.back.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@SuppressWarnings("unused")
public class RentalController implements RentalControllerOpenApi {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalMapper rentalMapper;

    @Override
    @PostMapping
    public void create(@RequestBody RentalCreateRequest request) {
        rentalService.create(request);
    }

    @Override
    @PutMapping
    public void update(@RequestBody RentalUpdateRequest request) {
        rentalService.update(request);

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RentalDetailRequest> findById(@PathVariable Long id) {
        return new ResponseEntity<>(rentalMapper.toRentalDetailRequest(rentalService.findById(id)), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public void returnBook(RentalDevolution request) {
        rentalService.returnBook(request);
    }


    @Override
    @GetMapping
    public ResponseEntity<List<RentalResponseRequest>> getAll() {
        return new ResponseEntity<>(rentalService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/returned")
    public ResponseEntity<List<RentalResponseRequest>> getReturned() {
        return new ResponseEntity<>(rentalService.getReturned(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/pending")
    public ResponseEntity<List<RentalResponseRequest>> getPending() {
        return new ResponseEntity<>(rentalService.getPending(), HttpStatus.OK);
    }
}
