package bookstore.back.controllers;

import bookstore.back.io.book.BookCreateRequest;
import bookstore.back.io.book.BookResponseRequest;
import bookstore.back.io.book.BookUpdateRequest;
import bookstore.back.services.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@SuppressWarnings("unused")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess method return")
    })
    @PostMapping
    public void create(@RequestBody BookCreateRequest request) {
        bookService.create(request);
    }

    @ApiOperation(value = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @PutMapping
    public void update(@RequestBody BookUpdateRequest request) { bookService.update(request); }


    @ApiOperation(value = "List all the books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    public ResponseEntity<List<BookResponseRequest>> getAll(){
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "List all the deleted books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping("/deleted")
    public ResponseEntity<List<BookResponseRequest>> getAllDeleted(){
        return new ResponseEntity<>(bookService.getAllDeleted(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @ApiOperation(value = "Delete permanently the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @DeleteMapping("/deleted/{id}")
    public void deletePermanently(@PathVariable Long id) {
        bookService.deletePermanently(id);
    }

}
