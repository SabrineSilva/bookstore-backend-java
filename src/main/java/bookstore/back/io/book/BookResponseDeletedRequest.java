package bookstore.back.io.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDeletedRequest {
    private Integer id;
    private String name;
    private String author;
    private Integer launchDate;
    private Integer totalQuantity;
    private Integer totalTimesRented;
    private String publisherName;

}
