package bookstore.back.io.publisher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherCreateRequest {
    private String name;
    private String city;
}
