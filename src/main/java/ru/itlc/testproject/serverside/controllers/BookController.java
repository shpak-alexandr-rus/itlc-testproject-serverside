package ru.itlc.testproject.serverside.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itlc.testproject.serverside.dataobjects.Book;
import ru.itlc.testproject.serverside.repositories.interfaces.BookRepository;
import ru.itlc.testproject.serverside.responses.BookPaginationResponse;
import ru.itlc.testproject.serverside.responses.BooleanResponse;

@Controller
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepo;

    @Autowired
    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book result = bookRepo.save(book).orElse(null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<BookPaginationResponse> getAllBooks(@RequestParam int page, @RequestParam int pageSize,
                                                              @RequestParam String sortingColumn, @RequestParam String sortingDirection) {
        try {
            return new ResponseEntity<>(bookRepo.findAll(page, pageSize, sortingColumn, sortingDirection), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book result = bookRepo.findById(id).orElse(null);
            return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BooleanResponse> updateBookById(@PathVariable Long id, @RequestBody Book book) {
        try {
            BooleanResponse result = bookRepo.updateById(id, book);
            return new ResponseEntity<>(result, result.isStatus() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BooleanResponse> deleteBookById(@PathVariable Long id) {
        try {
            BooleanResponse result = bookRepo.deleteById(id);
            return new ResponseEntity<>(result, result.isStatus() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
