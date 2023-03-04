package com.github.kylerequez.SampleRESTApi2.Repository;

import com.github.kylerequez.SampleRESTApi2.Model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String>
{
    @Query("select b from Book b where b.name = :name")
    BookEntity findByName(@Param(value = "name")String name);

    @Query("select b from Book b where b.id = :id or b.name = :name")
    BookEntity findByIdOrName(@Param(value = "id")String id , @Param(value = "name")String name);
}
