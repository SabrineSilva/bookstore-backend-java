package bookstore.back.controllers;

import bookstore.back.io.publisher.PublisherCreateRequest;
import bookstore.back.io.publisher.PublisherResponseRequest;
import bookstore.back.io.publisher.PublisherUpdateRequest;
import bookstore.back.services.PublisherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@SuppressWarnings("unused")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @ApiOperation(value = "Create a new publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PostMapping
    public void create(@RequestBody PublisherCreateRequest request){
        publisherService.create(request);
    }

    @ApiOperation(value = "Update the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
    public void update(@RequestBody PublisherUpdateRequest request){
        publisherService.update(request);
    }

    @ApiOperation(value = "List all the publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    public ResponseEntity<List<PublisherResponseRequest>> getAll(){
        return new ResponseEntity<>(publisherService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "List all the delete publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping("/deleted")
    public ResponseEntity<List<PublisherResponseRequest>> getAllDeleted(){
        return new ResponseEntity<>(publisherService.getAllDeleted(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        publisherService.delete(id);
    }

    @ApiOperation(value = "Delete permanently the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/deleted/{id}")
    public void deletePermanently(@PathVariable Long id){
        publisherService.deletePermanently(id);
    }
}
