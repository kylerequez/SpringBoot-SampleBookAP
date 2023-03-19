package com.github.kylerequez.SampleRESTApi2.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Book")
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            nullable = false,
            unique = true
    )
    private String id;

    @Column(
            name = "reference",
            nullable = false,
            unique = true
    )
    private String reference;

    @Column(
            name = "name",
            nullable = false,
            unique = true
    )
    private String name;


//    private List<GenreEntity> genres;

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties("books")
    private List<AuthorEntity> authors = new ArrayList<>();
}
