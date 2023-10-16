package bookstore.back.io.publisher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherUpdateRequest {
    private Integer id;
    private String name;
    private String city;
}
