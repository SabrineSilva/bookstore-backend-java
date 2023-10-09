package bookstore.back.controllers;

import bookstore.back.io.publisher.*;
import bookstore.back.open_api.PublisherControllerOpenApi;
import bookstore.back.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@SuppressWarnings("unused")
public class PublisherController implements PublisherControllerOpenApi {

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private PublisherMapper publisherMapper;

    @Override
    @PostMapping
    public void create(@RequestBody PublisherCreateRequest request){
        publisherService.create(request);
    }

    @Override
    @PutMapping
    public void update(@RequestBody PublisherUpdateRequest request){
        publisherService.update(request);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDetailRequest> findById(@PathVariable Long id) {
        return new ResponseEntity<>(publisherMapper.toPublisherDetailRequest(publisherService.findById(id)), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PublisherResponseRequest>> getAll(){
        return new ResponseEntity<>(publisherService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/deleted")
    public ResponseEntity<List<PublisherResponseRequest>> getAllDeleted(){
        return new ResponseEntity<>(publisherService.getAllDeleted(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        publisherService.delete(id);
    }

    @Override
    @DeleteMapping("/deleted/{id}")
    public void deletePermanently(@PathVariable Long id){
        publisherService.deletePermanently(id);
    }
}
