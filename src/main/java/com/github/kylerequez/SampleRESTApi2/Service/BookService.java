package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Model.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService
{
    ResponseEntity<List<Book>> getAllBooks();
    ResponseEntity<Book> getBookById(String id);
    ResponseEntity<Book> addBook(Book book);
    ResponseEntity<Book> putUpdateBook(String id, Book book);
    ResponseEntity<Book> patchUpdateBook(String id, Book book);
    void deleteBook(String id);
    void deleteAllBooks();
    ResponseEntity<Book> updateBookAuthors(String id, Book book);
}
