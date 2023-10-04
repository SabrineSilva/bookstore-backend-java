package bookstore.back.open_api;

import bookstore.back.io.book.BookCreateRequest;
import bookstore.back.io.book.BookResponseRequest;
import bookstore.back.io.book.BookUpdateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BookControllerOpenApi {

    @ApiOperation(value = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PostMapping
    void create(@RequestBody BookCreateRequest request);

    @ApiOperation(value = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
    void update(@RequestBody BookUpdateRequest request);

    @ApiOperation(value = "List all the books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    ResponseEntity<List<BookResponseRequest>> getAll();

    @ApiOperation(value = "List all the deleted books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping("/deleted")
    ResponseEntity<List<BookResponseRequest>> getAllDeleted();

    @ApiOperation(value = "Delete the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @ApiOperation(value = "Delete permanently the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/deleted/{id}")
    void deletePermanently(@PathVariable Long id);
}
