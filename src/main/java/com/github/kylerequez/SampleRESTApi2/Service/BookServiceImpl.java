package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Exceptions.AuthorNotFoundException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNameExistsException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNotFoundException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNullVariableException;
import com.github.kylerequez.SampleRESTApi2.Model.Author;
import com.github.kylerequez.SampleRESTApi2.Model.AuthorEntity;
import com.github.kylerequez.SampleRESTApi2.Model.Book;
import com.github.kylerequez.SampleRESTApi2.Model.BookEntity;
import com.github.kylerequez.SampleRESTApi2.Repository.AuthorRepository;
import com.github.kylerequez.SampleRESTApi2.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public ResponseEntity<List<Book>> getAllBooks() {
        List<BookEntity> bookEntities = this.bookRepository.findAll();
        return ResponseEntity.ok().body(
                        bookEntities.stream().map(
                                    e -> new Book(e)
                                )
                                .collect(Collectors.toList())
                );
    }

    @Override
    public ResponseEntity<Book> getBookById(@NonNull String id) {
        var bookEntity = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " does not exist."));
        return ResponseEntity
                .ok()
                .body(new Book(bookEntity));
    }

    @Override
    public ResponseEntity<Book> addBook(@NonNull Book book) {
        if(book.getName() == null || book.getReference() == null) {
            throw new BookNullVariableException("Book name and reference should not be empty.");
        }
        if(this.bookRepository.findByName(book.getName()) != null) {
            throw new BookNameExistsException("Book name \"" + book.getName() + "\" already exists.");
        }
        if(this.bookRepository.findByReference(book.getReference()) != null) {
            throw new BookNameExistsException("Book reference \"" + book.getReference() + "\" already exists.");
        }

        var bookEntity = BookEntity.builder()
                .name(book.getName())
                .reference(book.getReference())
                .build();
        bookEntity.setAuthors(
                book.getAuthors().stream().map(
                        author -> {
                            var authorEntity = this.authorRepository.findById(author.getId()).orElseThrow(() -> new AuthorNotFoundException("Author with id " + author.getId() + " does not exist."));
                            authorEntity.getBooks().add(bookEntity);
                            return authorEntity;
                        }
                ).collect(Collectors.toList())
        );

        return ResponseEntity.ok().body(new Book(this.bookRepository.save(bookEntity)));
    }

    @Override
    public ResponseEntity<Book> putUpdateBook(@NonNull String id, @NonNull Book book) {
        if(book.getName() == null || book.getReference() == null) {
            throw new BookNullVariableException("Book name and reference should not be empty.");
        }
        if(this.bookRepository.findByName(book.getName()) != null) {
            throw new BookNameExistsException("Book name \"" + book.getName() + "\" already exists.");
        }
        if(this.bookRepository.findByReference(book.getReference()) != null) {
            throw new BookNameExistsException("Book reference \"" + book.getReference() + "\" already exists.");
        }
        var bookEntity = this.bookRepository.findById(id).orElse(null);
        if(bookEntity == null) {
            bookEntity = BookEntity.builder()
                    .name(book.getName())
                    .reference(book.getReference())
                    .build();
        } else {
            bookEntity.setName(book.getName());
            bookEntity.setReference(book.getReference());
        }
        return ResponseEntity.ok().body(new Book(this.bookRepository.save(bookEntity)));
    }

    @Override
    public ResponseEntity<Book> patchUpdateBook(@NonNull String id, @NonNull Book book) {
        if(book.getName() == null || book.getReference() == null) {
            throw new BookNullVariableException("Book name and reference should not be empty.");
        }
        if(this.bookRepository.findByName(book.getName()) != null) {
            throw new BookNameExistsException("Book name \"" + book.getName() + "\" already exists.");
        }
        if(this.bookRepository.findByReference(book.getReference()) != null) {
            throw new BookNameExistsException("Book reference \"" + book.getReference() + "\" already exists.");
        }
        var bookEntity = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " does not exist."));
        bookEntity.setName(book.getName());
        bookEntity.setReference(book.getReference());
        return ResponseEntity.ok().body(new Book(this.bookRepository.save(bookEntity)));
    }

    @Override
    public void deleteBook(@NonNull String id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public void deleteAllBooks() {
        this.bookRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Book> updateBookAuthors(String id, Book book) {
        var bookEntity = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " does not exist."));
        bookEntity.setAuthors(
                book.getAuthors().stream().map(
                        author -> {
                            var authorEntity = this.authorRepository.findById(author.getId()).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " does not exist."));
                            authorEntity.setBooks(
                                    authorEntity.getBooks().stream()
                                            .filter(b -> !b.getId().equals(id))
                                            .collect(Collectors.toList())
                            );
                            authorEntity.getBooks().add(bookEntity);
                            return this.authorRepository.save(authorEntity);
                        }
                ).collect(Collectors.toList())
        );
        return ResponseEntity.ok().body(new Book(this.bookRepository.save(bookEntity)));
    }
}
