package bookstore.back.io.rental;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalResponseRequest {
    private Integer id;
    private String bookName;
    private String userName;
    private LocalDate rentalDate;
    private LocalDate deadline;
    private LocalDate returnDate;
    private Status status;

}
