package bookstore.back.services;

import bookstore.back.entities.PublisherEntity;
import bookstore.back.io.publisher.PublisherCreateRequest;
import bookstore.back.io.publisher.PublisherResponseRequest;
import bookstore.back.io.publisher.PublisherUpdateRequest;

import java.util.List;

public interface PublisherService {

    void create(PublisherCreateRequest request);

    void update(PublisherUpdateRequest request);

    List<PublisherResponseRequest> getAll();

    List<PublisherResponseRequest> getAllDeleted();

    PublisherEntity findById(Long id);

    PublisherEntity findByDeleteId(Long id);


    void delete(Long id);

    void deletePermanently(Long id);
}
