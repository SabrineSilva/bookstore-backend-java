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
    public void validate(BookEntity book){

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
        validationAmountUpdate(book.getTotalQuantity());
        validationSameDataUpdate(book);
    }

    private void validationSameData(BookEntity book) {
        String name = book.getName();
        String author = book.getAuthor();

         if (!bookRepository.findByName(name).isEmpty() && !bookRepository.findByAuthor(author).isEmpty()) {
            throw new BusinessException("Esse livro com o mesmo nome o mesmo autor já está cadastrado no sistema.");
        }

    }

    private void validationSameDataUpdate(BookEntity book) {
        String name = book.getName();
        String author = book.getAuthor();

        BookEntity existingBook = bookRepository.findBookByNameAndAuthor(name, author).orElse(null);
        if (existingBook != null && !existingBook.getId().equals(book.getId())) {
            throw new BusinessException("Esse livro com o mesmo nome o mesmo autor já está cadastrado no sistema.");
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

        if ( launchDate < 1 || launchDate > currentYear) {
            throw new BusinessException("O ano de lançamento deve ser um valor válido, antes ou igual ao ano atual");
        }

    }

    private void validationAmount(Integer amount){
        if (amount == null) {
            throw new BusinessException("A quantidade não pode ser nula.");
        }

        if (amount < 1) {
            throw new BusinessException("Para inserir um novo livro você deve ter pelo menos 1 exemplar.");
        }
    }

    private void validationAmountUpdate(Integer amount){
        if (amount == null) {
            throw new BusinessException("A quantidade não pode ser nula.");
        }

        if (amount < 0) {
            throw new BusinessException("A quantidade deve ser um valor válido (Maior ou igual a 0)");
        }

    }

    private void validateRelationship(Long id){

        List<Optional<RentalEntity>> rentals = rentalRepository.findByBookId(id);

        if (!rentals.isEmpty()){
            throw new BusinessException("Não é possível deletar, pois, há um aluguel associado à esse livro.");
        }
    }

    @Override
    public void validateForDelete(Long id) {
        validateRelationship(id);
    }

}
