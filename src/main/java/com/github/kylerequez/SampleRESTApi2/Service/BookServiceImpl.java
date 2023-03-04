package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Model.Book;
import com.github.kylerequez.SampleRESTApi2.Model.BookEntity;
import com.github.kylerequez.SampleRESTApi2.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService
{
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooks()
    {
        List<BookEntity> bookEntities = this.bookRepository.findAll();
        return ResponseEntity
                .ok()
                .body(
                        bookEntities.stream()
                                .map(e -> new Book(e))
                                .collect(Collectors.toList())
                );
    }

    @Override
    public ResponseEntity<Book> getBookById(@NonNull String id)
    {
        Optional<BookEntity> book = this.bookRepository.findById(id);
        if(!book.isPresent()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity
                .ok()
                .body(new Book(book.get()));
    }

    @Override
    public ResponseEntity<Book> addBook(@NonNull Book book)
    {
        return null;
    }

    @Override
    public ResponseEntity<Book> updateBook(@NonNull String id, @NonNull Book book)
    {
        return null;
    }

    @Override
    public void deleteBook(@NonNull String id)
    {
        this.bookRepository.deleteById(id);
    }
}
