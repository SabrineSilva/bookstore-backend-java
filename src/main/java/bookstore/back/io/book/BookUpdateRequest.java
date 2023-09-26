package bookstore.back.io.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateRequest {
    private Long id;
    private String name;
    private String author;
    private Integer launchDate;
    private Integer totalQuantity;
    private Long publisherId;
}
