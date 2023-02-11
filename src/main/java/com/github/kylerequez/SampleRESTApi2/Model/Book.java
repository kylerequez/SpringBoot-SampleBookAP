package com.github.kylerequez.SampleRESTApi2.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class Book
{
    private String id;
    private String name;

    public Book(BookEntity bookEntity)
    {
        this.id = bookEntity.getId();
        this.name = bookEntity.getName();
    }
}
