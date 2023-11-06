package bookstore.back.services.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.entities.UserEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.exception.NotFoundException;
import bookstore.back.io.book.*;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.services.BookService;
import bookstore.back.services.PublisherService;
import bookstore.back.validation.BookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookValidation bookValidation;

    @Override
    public void create(BookCreateRequest request) {
        BookEntity entity = new BookEntity();
        entity.setName(request.getName().trim());
        entity.setAuthor(request.getAuthor().trim());
        entity.setLaunchDate(request.getLaunchDate());
        entity.setTotalQuantity(request.getTotalQuantity());
        entity.setTotalTimesRented(0);
        entity.setAvailableQuantity(entity.getTotalQuantity());
        entity.setPublisher(publisherService.findById(request.getPublisherId()));
        bookValidation.validateForCreate(entity);
        bookRepository.save(entity);
    }

    @Override
    public void update(BookUpdateRequest request) {
        BookEntity entity = findById(request.getId());

        entity.setName(request.getName().trim());
        entity.setAuthor(request.getAuthor());
        entity.setLaunchDate(request.getLaunchDate());
        entity.setTotalQuantity(request.getTotalQuantity());
        updateAvailableQuantity(entity);
        entity.setPublisher(publisherService.findById(request.getPublisherId()));

        bookValidation.validateUpdate(entity);
        bookRepository.save(entity);


    }

    private void updateAvailableQuantity(BookEntity entity) {
        List<RentalEntity> currentRentals = rentalRepository.findAllByBookIdAndReturnDateIsNull(entity.getId());
        int totalRentedNow = currentRentals.size();
        entity.setAvailableQuantity(entity.getTotalQuantity() - totalRentedNow);

    }


    @Override
    public List<BookResponseRequest> getAll() {

        List<BookResponseRequest> booksWithAvailableQuantity = getBooksWithAvailableQuantity();

        return booksWithAvailableQuantity;
    }


    @Override
    public List<BookResponseDeletedRequest> getAllDeleted() {
        return bookRepository.findAllByIsDeletedTrue().stream().map(bookMapper::toBookResponseDeletedRequest).collect(Collectors.toList());
    }

    @Override
    public BookEntity findById(Integer id) {
        BookEntity book = bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("livro", id);
                });

        Integer totalRentedNow = rentalRepository.findAllByBookIdAndReturnDateIsNull(book.getId()).size();
        Integer totalTimesRented = rentalRepository.findByBookId(book.getId()).size();
        Integer availableQuantity = book.getTotalQuantity() - totalRentedNow;

        book.setAvailableQuantity(availableQuantity);
        book.setTotalTimesRented(totalTimesRented);

        return book;
    }


    @Override
    public BookEntity findByDeleteId(Integer id) {
        return bookRepository.findByIdAndIsDeletedTrue(id).orElseThrow(() -> new NotFoundException("o livro deletado", id));
    }

    @Override
    public void delete(Integer id) {
        BookEntity entity = findById(id);
        bookValidation.validateForDelete(id);
        entity.setDeleted(true);
        bookRepository.save(entity);
    }


    @Override
    public void deletePermanently(Integer id) {
        BookEntity entity = findByDeleteId(id);
        bookRepository.delete(entity);
    }

    public List<BookResponseRequest> getBooksWithAvailableQuantity() {
        List<BookEntity> books = bookRepository.findAllByIsDeletedFalse();
        List<BookResponseRequest> calculatedBooks = new ArrayList<>();

        for (BookEntity book : books) {
            Integer totalRentedNow = rentalRepository.findAllByBookIdAndReturnDateIsNull(book.getId()).size();
            Integer totalTimesRented = rentalRepository.findByBookId(book.getId()).size();
            BookResponseRequest response = getBookResponseRequest(book, totalRentedNow, totalTimesRented);

            calculatedBooks.add(response);
        }

        return calculatedBooks;
    }

    private static BookResponseRequest getBookResponseRequest(BookEntity book, Integer totalRentedNow, Integer totalTimesRented) {
        Integer availableQuantity = book.getTotalQuantity() - totalRentedNow;

        BookResponseRequest response = new BookResponseRequest();
        response.setId(book.getId());
        response.setName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setLaunchDate(book.getLaunchDate());
        response.setTotalQuantity(book.getTotalQuantity());
        response.setTotalTimesRented(totalTimesRented);
        response.setPublisherName(book.getPublisher().getName());
        response.setAvailableQuantity(availableQuantity);
        return response;
    }

}
