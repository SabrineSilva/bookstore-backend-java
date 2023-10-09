package bookstore.back.io.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseRequest {
    private Long id;
    private String name;
    private String author;
    private Integer launchDate;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private Integer totalTimesRented;
    private String publisherName;
}
