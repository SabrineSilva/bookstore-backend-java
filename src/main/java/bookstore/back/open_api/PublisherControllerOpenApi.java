package bookstore.back.open_api;

import bookstore.back.io.publisher.PublisherCreateRequest;
import bookstore.back.io.publisher.PublisherDetailRequest;
import bookstore.back.io.publisher.PublisherResponseRequest;
import bookstore.back.io.publisher.PublisherUpdateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PublisherControllerOpenApi {

    @ApiOperation(value = "Create a new publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PostMapping
      void create(@RequestBody PublisherCreateRequest request);

    @ApiOperation(value = "Update the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
      void update(@RequestBody PublisherUpdateRequest request);

    @GetMapping("/{id}")
    ResponseEntity<PublisherDetailRequest> findById(@PathVariable Integer id);

    @ApiOperation(value = "List all the publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
      ResponseEntity<List<PublisherResponseRequest>> getAll();

    @ApiOperation(value = "List all the delete publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping("/deleted")
      ResponseEntity<List<PublisherResponseRequest>> getAllDeleted();

    @ApiOperation(value = "Delete the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id);

    @ApiOperation(value = "Delete permanently the publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/deleted/{id}")
    void deletePermanently(@PathVariable Integer id);
}
