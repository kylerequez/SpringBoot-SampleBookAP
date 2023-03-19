package com.github.kylerequez.SampleRESTApi2.Service;

import com.github.kylerequez.SampleRESTApi2.Model.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService
{
    ResponseEntity<List<Author>> getAllAuthors();
    ResponseEntity<Author> getAuthorById(String id);
    ResponseEntity<Author> addAuthor(Author author);
    ResponseEntity<Author> putUpdateAuthor(String id, Author author);
    ResponseEntity<Author> patchUpdateAuthor(String id, Author author);
    void deleteAuthor(String id);
    void deleteAllAuthors();
    ResponseEntity<Author> updateAuthorBooks(String id, Author author);
}
