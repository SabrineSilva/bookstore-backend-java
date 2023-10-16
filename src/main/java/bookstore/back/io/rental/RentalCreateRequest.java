package bookstore.back.io.rental;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalCreateRequest {
    private LocalDate deadline;
    private Integer bookId;
    private Integer userId;
}
