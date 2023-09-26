package bookstore.back.validation;

import bookstore.back.entities.BookEntity;

public interface BookValidation {
    void validate(BookEntity book);

    void validateUpdate(BookEntity entity);

    void validateForDelete(Long id);
}
