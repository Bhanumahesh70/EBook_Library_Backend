package com.ebook.Repository;

import com.ebook.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query("select a from Author a where a.name=:aname")
    public Author findByName(@Param("aname") String authorName);


}
