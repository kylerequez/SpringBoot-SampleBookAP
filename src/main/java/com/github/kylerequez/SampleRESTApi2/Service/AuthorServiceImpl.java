package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Exceptions.AuthorNotFoundException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.AuthorNullVariableException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNotFoundException;
import com.github.kylerequez.SampleRESTApi2.Model.Author;
import com.github.kylerequez.SampleRESTApi2.Model.AuthorEntity;
import com.github.kylerequez.SampleRESTApi2.Repository.AuthorRepository;
import com.github.kylerequez.SampleRESTApi2.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService{
    public final AuthorRepository authorRepository;
    public final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // Retrieve all the authors
    @Override
    public ResponseEntity<List<Author>> getAllAuthors() {
        // Using the java stream, convert all entities retrieved from the findAll() method to author DTO then return it
        return ResponseEntity
                .ok()
                .body(
                        this.authorRepository.findAll()
                        .stream()
                        .map(e -> new Author(e))
                        .collect(Collectors.toList())
                );
    }

    @Override
    public ResponseEntity<Author> getAuthorById(@NonNull String id) {
        AuthorEntity author = this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " does not exist"));
        return ResponseEntity.ok(new Author(author));
    }

    @Override
    public ResponseEntity<Author> addAuthor(@NonNull Author author)
    {
        if(author.getFirstname() == null || author.getMiddlename() == null || author.getLastname() == null){
            throw new AuthorNullVariableException("Author's name should not be empty");
        }
        AuthorEntity authorEntity = new AuthorEntity(author.getFirstname(), author.getMiddlename(), author.getLastname());
        if(author.getBooks() == null) {
            authorRepository.save(authorEntity);
            return ResponseEntity.ok().body(new Author(authorEntity));
        }
        authorEntity.setBooks(
                author.getBooks().stream().map(
                    book -> {
                        var bookEntity = this.bookRepository.findByIdAndName(book.getId(), book.getName());
                        if(bookEntity == null) {
                            throw new BookNotFoundException("Book with id " + book.getId() + " does not exist.");
                        }
                        bookEntity.getAuthors().add(authorEntity);
                        return bookEntity;
                    }
                ).collect(Collectors.toList())
        );
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }

    @Override
    public ResponseEntity<Author> putUpdateAuthor(@NonNull String id, @NonNull Author author)
    {
        var authorEntity = this.authorRepository.findById(id).orElse(null);
        if(authorEntity == null) {
            authorEntity = new AuthorEntity(author.getFirstname(), author.getMiddlename(), author.getLastname());
        } else {
            authorEntity = AuthorEntity.builder()
                    .id(id)
                    .firstname(author.getFirstname())
                    .middlename(author.getMiddlename())
                    .lastname(author.getLastname())
                    .build();
        }
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }

    @Override
    public ResponseEntity<Author> patchUpdateAuthor(@NonNull String id, @NonNull Author author)
    {
        var authorEntity = this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " does not exist."));
        authorEntity.setFirstname(author.getFirstname());
        authorEntity.setMiddlename(author.getMiddlename());
        authorEntity.setLastname(author.getLastname());
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }

    @Override
    public void deleteAuthor(@NonNull String id) {
        var authorEntity = this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " does not exist."));
        this.authorRepository.delete(authorEntity);
    }

    @Override
    public void deleteAllAuthors() {
        this.authorRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Author> updateAuthorBooks(String id, Author author) {
        var authorEntity = this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author with id " + id + " does not exist."));
        authorEntity.setBooks(
                author.getBooks().stream().map(
                        book -> {
                            var bookEntity = this.bookRepository.findByIdAndName(book.getId(), book.getName());
                            if(bookEntity == null) {
                                throw new BookNotFoundException("Book with id " + book.getId() + " does not exist.");
                            }
                            return bookEntity;
                        }
                ).collect(Collectors.toList())
        );
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }
}

