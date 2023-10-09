package bookstore.back.services.impl;

import bookstore.back.exception.NotFoundException;
import bookstore.back.io.publisher.*;
import bookstore.back.entities.PublisherEntity;
import bookstore.back.exception.BusinessException;
import bookstore.back.repositories.PublisherRepository;
import bookstore.back.services.PublisherService;
import bookstore.back.validation.PublisherValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherValidation publisherValidation;

    @Autowired
    private PublisherMapper publisherMapper;

    @Override
    public void create(PublisherCreateRequest request) {
        PublisherEntity entity = new PublisherEntity();
        entity.setName(request.getName().trim());
        entity.setCity(request.getCity().trim());
        publisherValidation.validateForCreate(entity);
        publisherRepository.save(entity);
    }

    @Override
    public List<PublisherResponseRequest> getAll() {
        return publisherRepository.findAllByIsDeletedFalse().stream().map(publisherMapper::toPublisherResponseRequest).collect(Collectors.toList());
    }

    @Override
    public List<PublisherResponseRequest> getAllDeleted() {
        return publisherRepository.findAllByIsDeletedTrue().stream().map(publisherMapper::toPublisherResponseRequest).collect(Collectors.toList());
    }


    @Override
    public PublisherEntity findById(Long id) {
        return publisherRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Editora", id));
    }

    @Override
    public PublisherEntity findByDeleteId(Long id) {
        return publisherRepository.findByIdAndIsDeletedTrue(id)
                .orElseThrow(() -> new NotFoundException("Editora", id));
    }

    @Override
    public void update(PublisherUpdateRequest request) {
        PublisherEntity entity = findById(request.getId());
        entity.setName(request.getName().trim());
        entity.setCity(request.getCity().trim());
        publisherValidation.validateUpdate(entity);
        publisherRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        PublisherEntity entity = findById(id);
        publisherValidation.validateForDelete(id);
        entity.setDeleted(true);
        publisherRepository.save(entity);
    }

    @Override
    public void deletePermanently(Long id) {
        PublisherEntity entity = findByDeleteId(id);
        publisherRepository.delete(entity);
    }

}
