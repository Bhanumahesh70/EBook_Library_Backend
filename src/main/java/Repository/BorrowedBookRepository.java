package Repository;

import com.ebook.domain.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBookRepository  extends JpaRepository<BorrowedBook,Long> {
}
