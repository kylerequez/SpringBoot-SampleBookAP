package com.github.kylerequez.SampleRESTApi2.Repository;

import com.github.kylerequez.SampleRESTApi2.Model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, String>
{
}
