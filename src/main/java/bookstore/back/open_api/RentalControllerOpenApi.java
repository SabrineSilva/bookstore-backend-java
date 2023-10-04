package bookstore.back.open_api;

import bookstore.back.io.rental.RentalCreateRequest;
import bookstore.back.io.rental.RentalDevolution;
import bookstore.back.io.rental.RentalResponseRequest;
import bookstore.back.io.rental.RentalUpdateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RentalControllerOpenApi {

    @ApiOperation(value = "Create a new rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess method return")
    })
    @PostMapping
    void create(@RequestBody RentalCreateRequest request);

    @ApiOperation(value = "Update a rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
    void update(@RequestBody RentalUpdateRequest request);

    @ApiOperation(value = "Return a rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping("/{id}")
    void returnBook(RentalDevolution request);

    @ApiOperation(value = "List all the rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    ResponseEntity<List<RentalResponseRequest>> getAll();

    @ApiOperation(value = "List all the returned rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping("/returned")
    ResponseEntity<List<RentalResponseRequest>> getReturned();

    @ApiOperation(value = "List all the pending rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping("/pending")
    ResponseEntity<List<RentalResponseRequest>> getPending();
}
