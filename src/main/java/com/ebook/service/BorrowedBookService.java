package com.ebook.service;

import com.ebook.Repository.BookRepository;
import com.ebook.Repository.BorrowedBookRepository;
import com.ebook.Repository.FineRepository;
import com.ebook.Repository.UserRepository;
import com.ebook.domain.*;
import com.ebook.dto.BorrowedBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import java.util.Optional;

@Service
public class BorrowedBookService extends AbstractCRUDService<BorrowedBook,BorrowedBookDTO,Long> {

    private static final Logger logger = Logger.getLogger(BorrowedBookService.class.getName());
    private final BorrowedBookRepository borrowedBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final FineRepository fineRepository;
    @Autowired
    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository,
                               BookRepository bookRepository,
                               UserRepository userRepository,
                               FineRepository fineRepository) {
        super(borrowedBookRepository);
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.fineRepository = fineRepository;
    }

    @Override
    // Partial Update (Patch)
    public BorrowedBook patchUpdate(Long id, BorrowedBookDTO updatedBorrowedBookDTO) {
        logger.info("Running BorrowedBookService.patchUpdate()");

        BorrowedBook borrowedBook = borrowedBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowed Book not found with id: " + id));

        // Update only provided fields
        if (updatedBorrowedBookDTO.getBorrowDate() != null) borrowedBook.setBorrowDate(updatedBorrowedBookDTO.getBorrowDate());
        if (updatedBorrowedBookDTO.getReturnDate() != null) borrowedBook.setReturnDate(updatedBorrowedBookDTO.getReturnDate());
        if (updatedBorrowedBookDTO.getReturnedOn() != null) borrowedBook.setReturnedOn(updatedBorrowedBookDTO.getReturnedOn());
        if (updatedBorrowedBookDTO.getStatus() != null) borrowedBook.setStatus(BorrowStatus.valueOf(updatedBorrowedBookDTO.getStatus()));

        /**
        // Handle book relation (if bookId is provided)
        if (updatedBorrowedBookDTO.getBookId() != null) {
            Book book = bookRepository.findById(updatedBorrowedBookDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + updatedBorrowedBookDTO.getBookId()));
            borrowedBook.setBook(book);
        }

        // Handle user relation (if userId is provided)
        if (updatedBorrowedBookDTO.getUserId() != null) {
            User user = userRepository.findById(updatedBorrowedBookDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + updatedBorrowedBookDTO.getUserId()));
            borrowedBook.setUser(user);
        }

        // Handle fine relation (if fineId is provided)
        if (updatedBorrowedBookDTO.getFineId() != null) {
            Fine fine = fineRepository.findById(updatedBorrowedBookDTO.getFineId())
                    .orElseThrow(() -> new RuntimeException("Fine not found with id: " + updatedBorrowedBookDTO.getFineId()));
            borrowedBook.setFine(fine);
        }
    **/
        return borrowedBookRepository.save(borrowedBook);
    }

    @Override
    // Convert BorrowedBook entity to BorrowedBookDTO
    public BorrowedBookDTO convertToDTO(BorrowedBook borrowedBook) {
        logger.info("Converting BorrowedBook entity to BorrowedBookDTO");

        BorrowedBookDTO dto = new BorrowedBookDTO();
        dto.setId(borrowedBook.getId());
        dto.setBorrowDate(borrowedBook.getBorrowDate());
        dto.setReturnDate(borrowedBook.getReturnDate());
        dto.setReturnedOn(borrowedBook.getReturnedOn());
        dto.setStatus(borrowedBook.getStatus().name());

        // Set only IDs for related entities
        if (borrowedBook.getUser() != null) dto.setUserId(borrowedBook.getUser().getId());
        if (borrowedBook.getBook() != null) dto.setBookId(borrowedBook.getBook().getId());
        if (borrowedBook.getFine() != null) dto.setFineId(borrowedBook.getFine().getId());

        return dto;
    }

    @Override
    // Convert BorrowedBookDTO to BorrowedBook entity
    public BorrowedBook convertToEntity(BorrowedBookDTO borrowedBookDTO) {
        logger.info("Converting BorrowedBookDTO to BorrowedBook entity");

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBorrowDate(borrowedBookDTO.getBorrowDate());
        borrowedBook.setReturnDate(borrowedBookDTO.getReturnDate());
        borrowedBook.setReturnedOn(borrowedBookDTO.getReturnedOn());
        borrowedBook.setStatus(BorrowStatus.valueOf(borrowedBookDTO.getStatus()));

        /**
        // Set related entities if IDs are provided
        if (borrowedBookDTO.getBookId() != null) {
            Book book = bookRepository.findById(borrowedBookDTO.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + borrowedBookDTO.getBookId()));
            borrowedBook.setBook(book);
        }

        if (borrowedBookDTO.getUserId() != null) {
            User user = userRepository.findById(borrowedBookDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + borrowedBookDTO.getUserId()));
            borrowedBook.setUser(user);
        }

        if (borrowedBookDTO.getFineId() != null) {
            Fine fine = fineRepository.findById(borrowedBookDTO.getFineId())
                    .orElseThrow(() -> new RuntimeException("Fine not found with id: " + borrowedBookDTO.getFineId()));
            borrowedBook.setFine(fine);
        }
        **/

        return borrowedBook;
    }
    /**
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
    **/
}
