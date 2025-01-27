package Repository;

import com.ebook.domain.Author;
import com.ebook.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("select b from Book b where b.name=:bname")
    public Book findByName(@Param("bname") String BookName);

}
