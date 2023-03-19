package com.github.kylerequez.SampleRESTApi2.Controller;

import com.github.kylerequez.SampleRESTApi2.Model.Book;
import com.github.kylerequez.SampleRESTApi2.Service.BookServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks()
    {
        return this.bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id)
    {
        return this.bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book)
    {
        return this.bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> putUpdateBook(@PathVariable String id, @RequestBody Book book)
    {
        return this.bookService.putUpdateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id)
    {
        this.bookService.deleteBook(id);
    }

    @DeleteMapping
    public void deleteAllBooks()
    {
        this.bookService.deleteAllBooks();
    }

    @PatchMapping("/{id}/authors")
    public ResponseEntity<Book> updateBookAuthors(@PathVariable String id, @RequestBody Book book)
    {
        return this.bookService.updateBookAuthors(id, book);
    }
}
