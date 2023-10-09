package bookstore.back.io.book;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class BookDetailRequest extends BookResponseRequest{
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
