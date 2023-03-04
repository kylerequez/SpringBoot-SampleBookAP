package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Model.Author;
import com.github.kylerequez.SampleRESTApi2.Model.AuthorEntity;
import com.github.kylerequez.SampleRESTApi2.Model.Book;
import com.github.kylerequez.SampleRESTApi2.Model.BookEntity;
import com.github.kylerequez.SampleRESTApi2.Repository.AuthorRepository;
import com.github.kylerequez.SampleRESTApi2.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService{
    public AuthorRepository authorRepository;
    public BookRepository bookRepository;

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

    // Retrieve a certain author using their id
    @Override
    public ResponseEntity<Author> getAuthorById(@NonNull String id)
    {
        // Find the author entity using the id
        AuthorEntity author = this.authorRepository.findById(id).orElse(null);
        // If the author is null, return NO_CONTENT
        if(author == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        // Else, return the author entity as an author DTO
        return ResponseEntity
                .ok()
                .body(new Author(author));
    }

    // Add an author
    @Override
    public ResponseEntity<Author> addAuthor(@NonNull Author author)
    {
        // Create an author entity
        AuthorEntity authorEntity = new AuthorEntity(author.getFirstname(), author.getMiddlename(), author.getLastname());
        // If author does not have any books, save the author
        if(author.getBooks() == null) {
            authorRepository.save(authorEntity);
            return ResponseEntity.ok().body(new Author(authorEntity));
        }
        // Else, store the book entities inside a list
        List<BookEntity> books = new ArrayList<>();
        // Loop through the list of books
        for(Book book: author.getBooks()) {
            // Per book, look for the entity using its id
            var bookEntity = this.bookRepository.findByIdOrName(book.getId(), book.getName());
            // If the bookEntity is null
            if (bookEntity == null) {
                // Save a list for the authors and put the authorEntity inside it
                List<AuthorEntity> authors = Arrays.asList(authorEntity);
                // Create a new Book Entity
                bookEntity = BookEntity.builder()
                        .name(book.getName())
                        .reference(book.getReference())
                        .authors(authors)
                        .build();
            } else {
                System.out.println(book.getName() + " BOOK EXISTS");
                // Else, the book is not empty
                // Save a list for the authors and put the existing author entities inside it
                List<AuthorEntity> authors = bookEntity.getAuthors();
                // Add the new author entity to the list
                authors.add(authorEntity);
                // Update all the necessary data
                bookEntity.setName(book.getName());
                bookEntity.setReference(book.getReference());
                bookEntity.setAuthors(authors);
            }
            // Save the book
            this.bookRepository.save(bookEntity);
            // Store the book entity inside the list
            books.add(bookEntity);
        }
        // Set the books of authorEntity using the list
        authorEntity.setBooks(books);
        // Save and then return the author entity as an author DTO
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }

    // Update the author if he/she exists in the database, else create the author
    @Override
    public ResponseEntity<Author> putUpdateAuthor(String id, Author author)
    {
        // Using the id, search for the author
        var authorEntity = this.authorRepository.findById(id).orElse(null);
        // If the author is null
        if(authorEntity == null) {
            // Create a new author entity and place it inside updatedAuthor
            authorEntity = new AuthorEntity(author.getFirstname(), author.getMiddlename(), author.getLastname());
        } else {
            // If the author is present
            // Change the necessary information
            authorEntity.setFirstname(author.getFirstname());
            authorEntity.setMiddlename(author.getMiddlename());
            authorEntity.setLastname(author.getLastname());
        }

        // If the author does not have books
        if(author.getBooks() == null) {
            // Save the author then return the entity as an author DTO
            return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
        }

        // Else, create a list to store the book entities
        List<BookEntity> books = new ArrayList<>();
        // For each book that the author has
        for(Book book: author.getBooks())
        {
            // Retrieve the entity
            var bookEntity = this.bookRepository.findByIdOrName(book.getId(), book.getName());
            // If the book entity is null
            if (bookEntity == null) {
                // Create a list with the updatedAuthor entity
                List<AuthorEntity> authors = Arrays.asList(authorEntity);
                // Create the book entity using the new information of the book
                bookEntity = BookEntity.builder()
                        .name(book.getName())
                        .reference(book.getReference())
                        .authors(authors)
                        .build();
            } else {
                bookEntity.setId(book.getId());
                bookEntity.setName(book.getName());
                bookEntity.setReference(book.getReference());
                List<AuthorEntity> authors = bookEntity.getAuthors();
                authors.add(authorEntity);
            }
            // Add the book entity to the list
            books.add(bookEntity);
            // Then save it in the repository
            this.bookRepository.save(bookEntity);
        }
        authorEntity.setBooks(books);
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }

    // Update the author if he/she exists in the database
    @Override
    public ResponseEntity<Author> patchUpdateAuthor(String id, Author author)
    {
        // Using the id, search for the author
        var authorEntity = this.authorRepository.findById(id).orElse(null);
        // If the author is null
        if(authorEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // If the author is present
        // Change the necessary information
        authorEntity.setFirstname(author.getFirstname());
        authorEntity.setMiddlename(author.getMiddlename());
        authorEntity.setLastname(author.getLastname());

        // If the author does not have books
        if(author.getBooks() == null) {
            // Save the author then return the entity as an author DTO
            return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
        }

        // Else, create a list to store the book entities
        List<BookEntity> books = new ArrayList<>();
        // For each book that the author has
        for(Book book: author.getBooks())
        {
            // Retrieve the entity
            var bookEntity = this.bookRepository.findByIdOrName(book.getId(), book.getName());
            // If the book entity is null
            if (bookEntity == null) {
                // Create a list with the updatedAuthor entity
                List<AuthorEntity> authors = Arrays.asList(authorEntity);
                // Create the book entity using the new information of the book
                bookEntity = BookEntity.builder()
                        .name(book.getName())
                        .reference(book.getReference())
                        .authors(authors)
                        .build();
            } else {
                bookEntity.setId(book.getId());
                bookEntity.setName(book.getName());
                bookEntity.setReference(book.getReference());
                List<AuthorEntity> authors = bookEntity.getAuthors();
                authors.add(authorEntity);
            }
            // Add the book entity to the list
            books.add(bookEntity);
            // Then save it in the repository
            this.bookRepository.save(bookEntity);
        }
        authorEntity.setBooks(books);
        return ResponseEntity.ok().body(new Author(this.authorRepository.save(authorEntity)));
    }

    @Override
    public void deleteAuthor(String id) {
        var authorEntity = this.authorRepository.findById(id);
        if(!authorEntity.isPresent()) {
            System.out.println("NO AUTHOR WITH ID " + id);
        } else {
            var deletedAuthor = authorEntity.get();
            for(BookEntity bookEntity: deletedAuthor.getBooks()) {
                bookEntity.getAuthors().remove(deletedAuthor);
                System.out.println("Author Removal for " + bookEntity.getName());
                System.out.println(this.bookRepository.save(bookEntity));
            }
            this.authorRepository.deleteById(id);
        }
    }
}
