package com.ebook.service;

import Repository.BookRepository;
import Repository.BorrowedBookRepository;
import com.ebook.domain.Book;
import com.ebook.domain.BorrowedBook;
import org.springframework.stereotype.Service;

@Service
public class BorrowedBookService extends AbstractCRUDService<BorrowedBook,Long> {

    private final BorrowedBookRepository borrowedBookRepository;

    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository){
        super(borrowedBookRepository);
        this.borrowedBookRepository=borrowedBookRepository;
    }

    @Override
    public void update(Long id, Book updatedBook) {

    }
}
