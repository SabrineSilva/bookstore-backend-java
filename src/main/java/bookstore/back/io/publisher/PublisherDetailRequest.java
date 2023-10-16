package bookstore.back.io.publisher;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PublisherDetailRequest extends PublisherResponseRequest {

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;


}
