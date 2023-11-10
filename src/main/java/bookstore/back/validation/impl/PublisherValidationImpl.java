package bookstore.back.validation.impl;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.PublisherEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.BookRepository;
import bookstore.back.repositories.PublisherRepository;
import bookstore.back.validation.PublisherValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasText(publisher.getName())) {
            errors.add("'Nome' não pode ser nulo.");
        } else if (publisher.getName().length() > 50) {
            errors.add("Tamanho excedido no campo 'nome'. O máximo é de 50 caracteres.");
        } else if (publisherRepository.findPublisherByNameAndIsDeletedFalse(publisher.getName()).isPresent()) {
            errors.add("Já existe uma editora cadastrada com o mesmo nome.");
            System.out.println(publisherRepository.findPublisherByNameAndIsDeletedFalse(publisher.getName()).get().getName());
        }

        if (!StringUtils.hasText(publisher.getCity())) {
            errors.add("'Cidade' não pode ser nulo.");
        } else if (publisher.getCity().length() > 50) {
            errors.add("Tamanho excedido no campo 'cidade'. O máximo é de 50 caracteres.");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }
    }

    @Override
    public void validateUpdate(PublisherEntity publisher) {
        List<String> errors = new ArrayList();

        if (publisher == null) {
            errors.add("Editora não pode ser nula.");
        } else {
            String newName = publisher.getName();

            if (!StringUtils.hasText(newName)) {
                errors.add("'Nome' não pode ser nulo.");
            } else if (newName.length() > 50) {
                errors.add("Tamanho excedido no campo 'nome'. O máximo é de 50 caracteres.");
            }

            PublisherEntity existingPublisher = publisherRepository.findPublisherByNameAndIsDeletedFalse(newName).orElse(null);

            if (existingPublisher != null && !existingPublisher.getId().equals(publisher.getId())) {
                errors.add("Já existe uma editora cadastrada com o mesmo nome.");
            }
        }
        if (!StringUtils.hasText(publisher.getCity())) {
            errors.add("'Cidade' não pode ser nulo.");
        } else if (publisher.getCity().length() > 50) {
            errors.add("Tamanho excedido no campo 'cidade'. O máximo é de 50 caracteres.");
        }

        if (!errors.isEmpty()) {
            throw new BusinessException(String.join(" ", errors));
        }
    }

    @Override
    public void validateForDelete(Integer id) {
        List<Optional<BookEntity>> books = bookRepository.findByPublisherId(id);

        if (!books.isEmpty()) {
            throw new BusinessException("Não é possível deletar, pois, há um livro associado a essa editora.");
        }
    }
}
