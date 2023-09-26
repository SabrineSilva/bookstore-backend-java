package bookstore.back.io.rental;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalCreateRequest {
    private LocalDate dueDate;
    private Long bookId;
    private Long userId;
}
