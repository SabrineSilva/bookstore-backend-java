package bookstore.back.validation.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.PublisherEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.services.PublisherService;
import bookstore.back.validation.BookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class BookValidationImpl implements BookValidation {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private PublisherService publisherService;


    @Override
    public void validateForCreate(BookEntity book) {

        validationName(book.getName());
        validationAuthor(book.getAuthor());
        validationLaunchDate(book.getLaunchDate());
        validationAmount(book.getTotalQuantity());
        validationSameData(book);
    }


    @Override
    public void validateUpdate(BookEntity book) {
        validationName(book.getName());
        validationAuthor(book.getAuthor());
        validationLaunchDate(book.getLaunchDate());
        validationTotalQuantityUpdate(book.getTotalQuantity(), book);
        validationSameDataUpdate(book);
    }

    private void validationSameData(BookEntity book) {
        String name = book.getName();
        String author = book.getAuthor();
        Integer publisherId = book.getPublisher().getId();

        List<BookEntity> existingBooks = bookRepository.findByNameAndAuthorAndPublisherId(name, author, publisherId);

        if (!existingBooks.isEmpty()) {
            throw new BusinessException("Esse livro com o mesmo nome e autor já está cadastrado no sistema dentro dessa editora.");
        }
    }


    private void validationSameDataUpdate(BookEntity book) {
        String name = book.getName();
        String author = book.getAuthor();
        Integer publisher = book.getPublisher().getId();

        BookEntity existingBook = bookRepository.findBookByNameAndAuthorAndPublisherId(name, author, publisher).orElse(null);
        if (existingBook != null && !existingBook.getId().equals(book.getId())) {
            throw new BusinessException("Esse livro com o mesmo nome o mesmo autor já está cadastrado no sistema dentro dessa editora.");
        }

    }


    private void validationName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BusinessException("'Nome' não pode ser nulo.");
        }
        if (name.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }

    }

    private void validationAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new BusinessException("'Autor' não pode ser nulo.");
        }
        if (author.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    private void validationLaunchDate(Integer launchDate) {

        if (launchDate == null) {
            throw new BusinessException("O ano de lançamento não pode ser nulo.");
        }

        int currentYear = LocalDate.now().getYear();

        if (launchDate < 1 || launchDate > currentYear) {
            throw new BusinessException("O ano de lançamento deve ser um valor válido, antes ou igual ao ano atual");
        }

    }

    private void validationAmount(Integer amount) {
        if (amount == null) {
            throw new BusinessException("A quantidade não pode ser nula.");
        }

        if (amount < 1) {
            throw new BusinessException("Para inserir um novo livro você deve ter pelo menos 1 exemplar.");
        }
    }

    private void validationTotalQuantityUpdate(Integer totalQuantity, BookEntity book) {
        List<RentalEntity> currentRentals = rentalRepository.findAllByBookIdAndReturnDateIsNull(book.getId());
        int totalRentedNow = currentRentals.size();

        if (totalQuantity == null) {
            throw new BusinessException("A quantidade não pode ser nula.");
        }

        if (totalQuantity < 0) {
            throw new BusinessException("A quantidade deve ser um valor válido (Maior ou igual a 0)");
        }

        if (totalQuantity < totalRentedNow) {
            throw new BusinessException("O livro tem " + totalRentedNow + " aluguéis ativos, portanto, a sua quantidade total é no mínimo " + totalRentedNow);
        }

    }

    private void validateRelationship(Integer id) {

        List<Optional<RentalEntity>> rentals = rentalRepository.findByBookId(id);

        if (!rentals.isEmpty()) {
            throw new BusinessException("Não é possível deletar, pois, há pelo menos um aluguel associado a esse livro.");
        }
    }

    @Override
    public void validateForDelete(Integer id) {
        validateRelationship(id);
    }

}
