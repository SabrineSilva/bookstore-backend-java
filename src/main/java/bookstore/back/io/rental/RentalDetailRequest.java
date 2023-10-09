package bookstore.back.io.rental;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class RentalDetailRequest extends RentalResponseRequest {

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
