package bookstore.back.io.publisher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublisherResponseRequest {
    private Integer id;
    private String name;
    private String city;
}
