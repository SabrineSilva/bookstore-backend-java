package bookstore.back.validation.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.services.PublisherService;
import bookstore.back.validation.BookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
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
        List<String> errors = new ArrayList<>();
        String name = book.getName();

        if (!StringUtils.hasText(book.getName()) || name == null) {
            errors.add("'Nome' não pode ser nulo.");
        } else if (book.getName().length() > 50) {
            errors.add("Tamanho excedido no campo 'nome'. O máximo é de 50 caracteres.");
        }

        if (!StringUtils.hasText(book.getAuthor())) {
            errors.add("'Autor' não pode ser nulo.");
        } else if (book.getAuthor().length() > 50) {
            errors.add("Tamanho excedido no campo 'autor'. O máximo é de 50 caracteres.");
        }

        if (book.getLaunchDate() == null) {
            errors.add("O ano de lançamento não pode ser nulo.");
        } else {
            int currentYear = LocalDate.now().getYear();
            if (book.getLaunchDate() < 1 || book.getLaunchDate() > currentYear) {
                errors.add("O ano de lançamento deve ser um valor válido, antes ou igual ao ano atual.");
            }
        }

        if (book.getTotalQuantity() == null) {
            errors.add("A quantidade não pode ser nula.");
        } else if (book.getTotalQuantity() < 1) {
            errors.add("Para inserir um novo livro você deve ter pelo menos 1 exemplar.");
        }

        validationSameData(book, errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }
    }

    @Override
    public void validateUpdate(BookEntity book) {
        List<String> errors = new ArrayList<>();


        if (!StringUtils.hasText(book.getName())) {
            errors.add("'Nome' não pode ser nulo.");
        } else if (book.getName().length() > 50) {
            errors.add("Tamanho excedido no campo 'nome'. O máximo é de 50 caracteres.");
        }

        if (!StringUtils.hasText(book.getAuthor())) {
            errors.add("'Autor' não pode ser nulo.");
        } else if (book.getAuthor().length() > 50) {
            errors.add("Tamanho excedido no campo 'autor'. O máximo é de 50 caracteres.");
        }

        if (book.getLaunchDate() != null) {
            int currentYear = LocalDate.now().getYear();
            if (book.getLaunchDate() < 1 || book.getLaunchDate() > currentYear) {
                errors.add("O ano de lançamento deve ser um valor válido, antes ou igual ao ano atual.");
            }
        }

        if (book.getTotalQuantity() == null) {
            errors.add("A quantidade não pode ser nula.");
        }

        if (book.getTotalQuantity() != null) {
            List<RentalEntity> currentRentals = rentalRepository.findAllByBookIdAndReturnDateIsNull(book.getId());
            int totalRentedNow = currentRentals.size();

            if (book.getTotalQuantity() < 0) {
                errors.add("A quantidade deve ser um valor válido (Maior ou igual a 0).");
            }

            if (book.getTotalQuantity() < totalRentedNow) {
                errors.add("O livro tem " + totalRentedNow + " aluguéis ativos, portanto, a sua quantidade total é no mínimo " + totalRentedNow + ".");
            }
        }

        validationSameDataUpdate(book, errors);

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }

    }

    private void validationSameData(BookEntity book, List<String> errors) {
        String name = book.getName();
        String author = book.getAuthor();
        Integer publisherId = book.getPublisher().getId();

        List<BookEntity> existingBooks = bookRepository.findByNameAndAuthorAndPublisherIdAndIsDeletedFalse(name, author, publisherId);

        if (!existingBooks.isEmpty()) {
            errors.add("Esse livro com o mesmo nome e autor já está cadastrado no sistema dentro dessa editora.");
        }
    }

    private void validationSameDataUpdate(BookEntity book, List<String> errors) {
        String name = book.getName();
        String author = book.getAuthor();
        Integer publisher = book.getPublisher().getId();

        BookEntity existingBook = bookRepository.findBookByNameAndAuthorAndPublisherIdAndIsDeletedFalse(name, author, publisher).orElse(null);
        if (existingBook != null && !existingBook.getId().equals(book.getId())) {
            errors.add("Esse livro com o mesmo nome e o mesmo autor já está cadastrado no sistema dentro dessa editora.");
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
