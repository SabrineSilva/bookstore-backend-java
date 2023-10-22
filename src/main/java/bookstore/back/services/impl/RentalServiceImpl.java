package bookstore.back.services.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.exception.NotFoundException;
import bookstore.back.io.rental.*;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.repositories.UserRepository;
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

    @Override
    public void create(RentalCreateRequest request) {
        RentalEntity rental = new RentalEntity();
        BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new NotFoundException("o livro", request.getBookId()));

        rental.setBook(book);
        rental.setUser(userService.findById(request.getUserId()));
        rental.setDeadline(request.getDeadline());
        rental.setRentalDate(LocalDate.now());
        rentalValidation.validateForCreate(rental);
        rental.getBook().setAvailableQuantity(book.getAvailableQuantity());
        rental.setStatus(getStatus(rental.getDeadline(), rental.getReturnDate()));
        bookRepository.save(book);
        rentalRepository.save(rental);
    }

    @Override
    public void increaseDeadline(RentalUpdateRequest request) {
        RentalEntity entity = findByIdAndReturnDateIsNull(request.getId());
        LocalDate existingDeadline = entity.getDeadline();
        if (entity.getStatus() == Status.PENDING_ON_TIME) {
            entity.setDeadline(request.getDeadline());
            rentalValidation.validateUpdate(entity, existingDeadline);
            entity.setStatus(getStatus(entity.getDeadline(), entity.getReturnDate()));
            rentalRepository.save(entity);
        } else {
            throw new BusinessException("Você só pode renovar aluguéis pendentes e no prazo).");
        }
    }

    @Override
    public void returnBook(RentalDevolution request) {
        RentalEntity entity = findByIdAndReturnDateIsNull(request.getId());
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

    @Override
    public RentalEntity findById(Integer id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("o aluguel", id));
    }

    private RentalEntity findByIdAndReturnDateIsNull(Integer id) {
        return rentalRepository.findByIdAndReturnDateIsNull(id).orElseThrow(() -> new NotFoundException("o aluguel", id));
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
