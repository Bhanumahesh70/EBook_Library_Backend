package Repository;

import com.ebook.domain.Author;
import com.ebook.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublisherRepository  extends JpaRepository<Publisher,Long> {
    @Query("select p from Publisher p where p.name=:pname")
    public Publisher findByName(@Param("pname") String publisherName);

}
