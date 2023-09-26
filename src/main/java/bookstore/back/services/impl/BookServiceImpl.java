package bookstore.back.services.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.io.book.BookCreateRequest;
import bookstore.back.io.book.BookMapper;
import bookstore.back.io.book.BookResponseRequest;
import bookstore.back.io.book.BookUpdateRequest;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.RentalRepository;
import bookstore.back.services.BookService;
import bookstore.back.services.PublisherService;
import bookstore.back.services.RentalService;
import bookstore.back.validation.BookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void create(BookCreateRequest request){
       BookEntity entity = new BookEntity();
       entity.setName(request.getName().trim());
       entity.setAuthor(request.getAuthor().trim());
       entity.setLaunchDate(request.getLaunchDate());
       entity.setTotalQuantity(request.getTotalQuantity());
       entity.setTotalRented(0);
       entity.setAvailableQuantity(entity.getTotalQuantity());
       entity.setPublisher(publisherService.findById(request.getPublisherId()));
       bookValidation.validate(entity);
       bookRepository.save(entity);
   }

    @Override
    public void update(BookUpdateRequest request) {
            BookEntity entity = findById(request.getId());
            entity.setName(request.getName().trim());
            entity.setAuthor(request.getAuthor());
            entity.setLaunchDate(request.getLaunchDate());
            entity.setTotalQuantity(request.getTotalQuantity());
            entity.setPublisher(publisherService.findById(request.getPublisherId()));
            bookValidation.validateUpdate(entity);
            bookRepository.save(entity);
    }


    @Override
    public List<BookResponseRequest> getAll() {
        return bookRepository.findAllByIsDeletedFalse().stream().map(bookMapper::toBookResponseRequest).collect(Collectors.toList());
    }

    @Override
    public List<BookResponseRequest> getAllDeleted() {
        return bookRepository.findAllByIsDeletedTrue().stream().map(bookMapper::toBookResponseRequest).collect(Collectors.toList());
    }

    @Override
    public BookEntity findById(Long id){
        return bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new BusinessException("Não foi possível encontrar o livro."));
    }



    @Override
    public BookEntity findByDeleteId(Long id){
        return bookRepository.findByIdAndIsDeletedTrue(id)
                .orElseThrow(() -> new BusinessException("Não foi possível encontrar o livro deletado."));
    }

    @Override
    public void delete(Long id) {
        BookEntity entity = findById(id);
        bookValidation.validateForDelete(id);
        entity.setDeleted(true);
        bookRepository.save(entity);
    }


    @Override
    public void deletePermanently(Long id) {
        BookEntity entity = findByDeleteId(id);
        bookRepository.delete(entity);
    }

}
