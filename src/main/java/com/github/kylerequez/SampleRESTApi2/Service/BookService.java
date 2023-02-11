package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Model.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService
{
    ResponseEntity<List<Book>> getAllBooks();
    ResponseEntity<Book> getBookById(String id);
    ResponseEntity<Book> addBook(Book book);
    ResponseEntity<Book> updateBook(String id, Book book);
    void deleteBook(String id);
}
