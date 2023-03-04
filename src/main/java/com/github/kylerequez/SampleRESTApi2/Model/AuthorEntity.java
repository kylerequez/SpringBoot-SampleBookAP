package com.github.kylerequez.SampleRESTApi2.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Author")
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorEntity
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
            name = "firstname",
            nullable = false
    )
    private String firstname;

    @Column(
            name = "middlename",
            nullable = false
    )
    private String middlename;

    @Column(
            name = "lastname",
            nullable = false
    )
    private String lastname;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(
                    name = "author_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_author_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_book_id")
            )
    )
    @JsonIgnoreProperties("authors")
    private List<BookEntity> books = new ArrayList<>();

    public AuthorEntity(String firstname, String middlename, String lastname)
    {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }
}
