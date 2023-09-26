package bookstore.back.services.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.io.rental.RentalCreateRequest;
import bookstore.back.io.rental.RentalMapper;
import bookstore.back.io.rental.RentalResponseRequest;
import bookstore.back.io.rental.Status;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.services.BookService;
import bookstore.back.services.RentalService;
import bookstore.back.services.UserService;
import bookstore.back.validation.RentalValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalValidation rentalValidation;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Override
    public void create(RentalCreateRequest request) {
        RentalEntity entity = new RentalEntity();
        entity.setBook(bookService.findById(request.getBookId()));
        entity.setUser(userService.findById(request.getUserId()));
        entity.setDeadLine(request.getDueDate());
        entity.setRentalDate(LocalDate.now());

        BookEntity book = entity.getBook();
        Integer totalRented = book.getTotalRented();
        Integer availableQuantity = book.getAvailableQuantity();

        book.setTotalRented(totalRented + 1);
        book.setAvailableQuantity(availableQuantity - 1);

        entity.setStatus(getStatus(request.getDueDate()));
        rentalValidation.validate(entity);
        bookRepository.save(book);
        rentalRepository.save(entity);
    }

    @Override
    public List<RentalResponseRequest> getAll() {
        return rentalRepository.findAll().stream().map(rentalMapper::toRentalResponseRequest).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        RentalEntity entity = findById(id);
        rentalRepository.delete(entity);
    }

    private RentalEntity findById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Não foi possível encontrar o aluguel."));
    }

    private Status getStatus(LocalDate dueDate){
        LocalDate today = LocalDate.now();
        if (today.isBefore(dueDate) || today.isEqual(dueDate)){
            return Status.PENDING_ON_TIME;
        } else {
            return Status.PENDING_LATE;
        }
    }

}
