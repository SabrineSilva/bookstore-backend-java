package bookstore.back.io.rental;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalUpdateRequest {
    private Integer id;
    private LocalDate deadline;

}
