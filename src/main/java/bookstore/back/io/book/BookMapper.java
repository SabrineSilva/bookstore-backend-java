package bookstore.back.io.book;

import bookstore.back.entities.BookEntity;
import bookstore.back.entities.RentalEntity;
import bookstore.back.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponseRequest toBookResponseRequest(BookEntity book) {
        BookResponseRequest response = new BookResponseRequest();
        response.setId(book.getId());
        response.setName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setLaunchDate(book.getLaunchDate());
        response.setTotalQuantity(book.getTotalQuantity());
        response.setAvailableQuantity(book.getAvailableQuantity());
        response.setTotalRented(book.getTotalRented());
        response.setPublisherName(book.getPublisher().getName());
        return response;
    }

}
