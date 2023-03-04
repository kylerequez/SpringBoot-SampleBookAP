package com.github.kylerequez.SampleRESTApi2.Controller;

import com.github.kylerequez.SampleRESTApi2.Model.Author;

import com.github.kylerequez.SampleRESTApi2.Service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController
{
    public AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors()
    {
        return this.authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String id)
    {
        return this.authorService.getAuthorById(id);
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author)
    {
        return this.authorService.addAuthor(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> putUpdateAuthor(@PathVariable String id, @RequestBody Author author)
    {
        return this.authorService.putUpdateAuthor(id, author);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> patchUpdateAuthor(@PathVariable String id, @RequestBody Author author)
    {
        return this.authorService.patchUpdateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable String id)
    {
        this.authorService.deleteAuthor(id);
    }
}
