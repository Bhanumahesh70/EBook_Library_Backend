package com.ebook.Repository;

import com.ebook.domain.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository  extends JpaRepository<BorrowedBook,Long> {
}
