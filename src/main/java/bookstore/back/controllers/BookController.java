package bookstore.back.controllers;

import bookstore.back.io.book.BookCreateRequest;
import bookstore.back.io.book.BookResponseRequest;
import bookstore.back.io.book.BookUpdateRequest;
import bookstore.back.open_api.BookControllerOpenApi;
import bookstore.back.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@SuppressWarnings("unused")
public class BookController implements BookControllerOpenApi {

    @Autowired
    private BookService bookService;

    @Override
    @PostMapping
    public void create(@RequestBody BookCreateRequest request) {
        bookService.create(request);
    }

    @Override
    @PutMapping
    public void update(@RequestBody BookUpdateRequest request) { bookService.update(request); }

    @Override
    @GetMapping
    public ResponseEntity<List<BookResponseRequest>> getAll(){
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/deleted")
    public ResponseEntity<List<BookResponseRequest>> getAllDeleted(){
        return new ResponseEntity<>(bookService.getAllDeleted(), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @Override
    @DeleteMapping("/deleted/{id}")
    public void deletePermanently(@PathVariable Long id) {
        bookService.deletePermanently(id);
    }

}
