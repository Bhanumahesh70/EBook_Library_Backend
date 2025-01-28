package com.ebook.service;

import Repository.BookRepository;
import Repository.BorrowedBookRepository;
import com.ebook.domain.Book;
import com.ebook.domain.BorrowedBook;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowedBookService extends AbstractCRUDService<BorrowedBook,Long> {

    private final BorrowedBookRepository borrowedBookRepository;

    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository){
        super(borrowedBookRepository);
        this.borrowedBookRepository=borrowedBookRepository;
    }

    @Override
    public void update(Long id, BorrowedBook updatedBorrowedBook) {
        Optional<BorrowedBook> existingBorrowedBookOpt = borrowedBookRepository.findById(id);

        if (existingBorrowedBookOpt.isPresent()) {
            BorrowedBook existingBorrowedBook = existingBorrowedBookOpt.get();

            // Update properties
            existingBorrowedBook.setBorrowDate(updatedBorrowedBook.getBorrowDate());
            existingBorrowedBook.setReturnDate(updatedBorrowedBook.getReturnDate());
            existingBorrowedBook.setReturnedOn(updatedBorrowedBook.getReturnedOn());
            existingBorrowedBook.setStatus(updatedBorrowedBook.getStatus());
            existingBorrowedBook.setUser(updatedBorrowedBook.getUser());
            existingBorrowedBook.setBook(updatedBorrowedBook.getBook());
            existingBorrowedBook.setFine(updatedBorrowedBook.getFine());

            // Save updated entity
          borrowedBookRepository.save(existingBorrowedBook);
        } else {
            throw new IllegalArgumentException("BorrowedBook with ID " + id + " not found");
        }
    }
}
