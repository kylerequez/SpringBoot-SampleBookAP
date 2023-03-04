package com.github.kylerequez.SampleRESTApi2.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author
{
    private String id;
    private String firstname;
    private String middlename;
    private String lastname;
    private List<Book> books;

    public Author(AuthorEntity authorEntity)
    {
        this.id = authorEntity.getId();
        this.firstname = authorEntity.getFirstname();
        this.middlename = authorEntity.getMiddlename();
        this.lastname = authorEntity.getLastname();
        this.books = authorEntity.getBooks() != null
                ? authorEntity.getBooks()
                    .stream()
                    .map(e -> new Book(e.getId(), e.getReference(), e.getName()))
                    .collect(Collectors.toList())
                : null;
    }
    public Author(String id, String firstname, String middlename, String lastname)
    {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }
}
