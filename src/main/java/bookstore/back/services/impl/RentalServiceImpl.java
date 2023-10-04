package bookstore.back.services.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.io.rental.*;
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
        RentalEntity rental = new RentalEntity();
        BookEntity book = bookService.findById(request.getBookId());
        Integer totalRented = book.getTotalRented();
        Integer availableQuantity = book.getAvailableQuantity();

        rental.setBook(book);
        rental.setUser(userService.findById(request.getUserId()));
        rental.setDeadLine(request.getDeadLine());
        rental.setRentalDate(LocalDate.now());
        rentalValidation.validate(rental);
        book.setTotalRented(totalRented + 1);
        rental.setStatus(getStatus(rental.getDeadLine(), rental.getReturnDate()));
        bookRepository.save(book);
        rentalRepository.save(rental);
    }

    @Override
    public void update(RentalUpdateRequest request) {
        RentalEntity entity = findById(request.getId());
        entity.setBook(bookService.findById(request.getBookId()));
        entity.setUser(userService.findById(request.getUserId()));
        entity.setDeadLine(request.getDeadLine());
        rentalValidation.validateUpdate(entity);
        entity.setStatus(getStatus(entity.getDeadLine(), entity.getReturnDate()));
        rentalRepository.save(entity);
    }

    @Override
    public void returnBook(RentalDevolution request) {
        RentalEntity entity = findById(request.getId());
        LocalDate today = LocalDate.now();

        entity.setReturnDate(today);

        rentalRepository.save(entity);
    }

    @Override
    public List<RentalResponseRequest> getAll() {
        return rentalRepository.findAll().stream().map(rentalMapper::toRentalResponseRequest).collect(Collectors.toList());

    }

    @Override
    public List<RentalResponseRequest> getReturned() {
        return rentalRepository.findAllByReturnDateIsNotNull().stream().map(rentalMapper::toRentalResponseRequest).collect(Collectors.toList());
    }

    @Override
    public List<RentalResponseRequest> getPending() {
        return rentalRepository.findAllByReturnDateIsNull().stream().map(rentalMapper::toRentalResponseRequest).collect(Collectors.toList());
    }

    private RentalEntity findById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Não foi possível encontrar o aluguel."));
    }

    private RentalEntity findReturnedRentals(Long id) {
        return rentalRepository.findByIdAndReturnDateIsNotNull(id)
                .orElseThrow(() -> new BusinessException("Não foi possível encontrar o aluguel. Certifique-se que você está tentando apagar um aluguel que já foi devolvido, ou verifique se está passando os padrões corretamente."));

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
