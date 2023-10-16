package bookstore.back.validation;

import bookstore.back.entities.BookEntity;

public interface BookValidation {
    void validateForCreate(BookEntity book);

    void validateUpdate(BookEntity entity);

    void validateForDelete(Integer id);
}
