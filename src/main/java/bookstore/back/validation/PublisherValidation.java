package bookstore.back.validation;

import bookstore.back.entities.PublisherEntity;

public interface PublisherValidation {
     void validateForCreate(PublisherEntity publisher);

     void validateUpdate(PublisherEntity publisher);

     void validateForDelete(Integer id);
}
