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
public class Book
{
    private String id;
    private String reference;
    private String name;
    private List<Author> authors;

    public Book(String id, String reference, String name)
    {
        this.id = id;
        this.reference = reference;
        this.name = name;
    }

    public Book(BookEntity bookEntity)
    {
        this.id = bookEntity.getId();
        this.name = bookEntity.getName();
        this.authors = bookEntity.getAuthors()
                .stream()
                .map(e -> new Author(e.getId(),
                        e.getFirstname(),
                        e.getMiddlename(),
                        e.getLastname())
                )
                .collect(Collectors.toList());
    }
}
