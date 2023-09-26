package bookstore.back.controllers;

import bookstore.back.io.rental.RentalCreateRequest;
import bookstore.back.io.rental.RentalResponseRequest;
import bookstore.back.services.RentalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@SuppressWarnings("unused")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @ApiOperation(value = "Create a new rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess method return")
    })
    @PostMapping
    public void create(@RequestBody RentalCreateRequest request) {
        rentalService.create(request);
    }

    @ApiOperation(value = "List all the rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    public ResponseEntity<List<RentalResponseRequest>> getAll(){
        return new ResponseEntity<>(rentalService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete the rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rentalService.delete(id);
    }
}
