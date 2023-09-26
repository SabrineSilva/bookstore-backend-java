package bookstore.back.validation;

import bookstore.back.entities.PublisherEntity;

public interface PublisherValidation {
     void validate(PublisherEntity publisher);

     void validateUpdate(PublisherEntity publisher);

     void validateForDelete(Long id);
}
