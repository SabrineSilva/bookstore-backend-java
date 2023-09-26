package bookstore.back.io.publisher;

import bookstore.back.entities.PublisherEntity;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherResponseRequest toPublisherResponseRequest(PublisherEntity publisher){
        PublisherResponseRequest response = new PublisherResponseRequest();
        response.setId(publisher.getId());
        response.setName(publisher.getName());
        response.setCity(publisher.getCity());
        return response;
    }
}
