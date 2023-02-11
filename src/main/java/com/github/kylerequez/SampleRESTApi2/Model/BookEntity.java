package com.github.kylerequez.SampleRESTApi2.Model;

import jakarta.persistence.*;
import lombok.*;

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
            name = "name",
            nullable = false,
            unique = true
    )
    private String name;
}
