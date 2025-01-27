package Repository;

import com.ebook.domain.Author;
import com.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository  extends JpaRepository<User,Long> {

    @Query("select u from User u where u.name=:uname")
    public User findByName(@Param("uname") String userName);

}
