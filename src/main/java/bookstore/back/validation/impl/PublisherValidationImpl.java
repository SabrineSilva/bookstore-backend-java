package bookstore.back.validation.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.PublisherEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.PublisherRepository;
import bookstore.back.validation.PublisherValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PublisherValidationImpl implements PublisherValidation {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void validateForCreate(PublisherEntity publisher) {
        validationName(publisher.getName());
        validationCity(publisher.getCity());
    }

    @Override
    public void validateUpdate(PublisherEntity publisher) {
        validationNameUpdate(publisher);
        validationCity(publisher.getCity());
    }

    @Override
    public void validateForDelete(Integer id) {
        validateRelationship(id);
    }

    private void validateRelationship(Integer id){

        List<Optional<BookEntity>> books = bookRepository.findByPublisherId(id);

        if (!books.isEmpty()){
            throw new BusinessException("Não é possível deletar, pois, há um livro associado à essa editora.");
        }
    }

    private void validationCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new BusinessException("'Cidade' não pode ser nulo.");
        }
        if (city.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }
    }

    private void validationName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BusinessException("'Nome' não pode ser nulo.");
        }
        if (name.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }
        if (publisherRepository.findPublisherByName(name).isPresent()){
            throw new BusinessException("Já existe uma editora cadastrada com o mesmo nome.");
        }
    }

    private void validationNameUpdate(PublisherEntity publisher) {
        if (publisher == null) {
            throw new BusinessException("Editora não pode ser nula.");
        }

        String newName = publisher.getName();

        if (newName == null || newName.isEmpty()) {
            throw new BusinessException("'Nome' não pode ser nulo.");
        }

        if (newName.length() > 50) {
            throw new BusinessException("Tamanho excedido. O máximo é de 50 caracteres.");
        }

        PublisherEntity existingPublisher = publisherRepository.findPublisherByName(newName).orElse(null);

        if (existingPublisher != null && !existingPublisher.getId().equals(publisher.getId())) {
            throw new BusinessException("Já existe uma editora cadastrada com o mesmo nome.");
        }
    }


}
