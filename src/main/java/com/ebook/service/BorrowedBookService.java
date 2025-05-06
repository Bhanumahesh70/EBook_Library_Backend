package com.ebook.service;
import com.ebook.Repository.BorrowedBookRepository;
import com.ebook.domain.*;
import com.ebook.dto.BookDTO;
import com.ebook.dto.BorrowedBookDTO;
import com.ebook.dto.FineDTO;
import com.ebook.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class BorrowedBookService extends AbstractCRUDService<BorrowedBook,BorrowedBookDTO,Long> {

    private static final Logger logger = Logger.getLogger(BorrowedBookService.class.getName());
    private final BorrowedBookRepository borrowedBookRepository;
    private final UserService userService;
    private final BookService bookService;
    private final ReservationService reservationService;
    @Autowired
    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository
                              ,UserService userService, BookService bookService,ReservationService reservationService) {
        super(borrowedBookRepository);
        this.borrowedBookRepository = borrowedBookRepository;
        this.reservationService = reservationService;
        this.userService = userService;
        this.bookService = bookService;
    }

    public double calculateBorrowCost(User user, Book book, int daysRequested) {
        //Free for initial 30 days. And 1$ for each day after that

        boolean hasBorrowedBefore = user.getBorrowedBooks().stream()
                .anyMatch(bb -> bb.getBook().equals(book));

        if (!hasBorrowedBefore) {
            int freeDays = 30;
            return (daysRequested > freeDays) ? (daysRequested - freeDays) * 1.0 : 0.0;
        }
        return daysRequested * 1.0;
    }
    public void createBorrowedBook(Reservation reservation){
        if(reservation.getStatus().toString().equals("APPROVED")) {
            logger.info("Creating new Borrowed Book");
            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBorrowDate(reservation.getReservationDate());
            borrowedBook.setExpectedReturnDate(borrowedBook.getBorrowDate().plusDays(reservation.getNumberOfDays()));
            borrowedBook.setStatus(BorrowStatus.BORROWED);
            borrowedBook.setBookBorrowCost(calculateBorrowCost(reservation.getUser(),reservation.getBook(), reservation.getNumberOfDays()));
            borrowedBook.setUser(userService.findById(reservation.getUser().getId()));
            borrowedBook.setBook(bookService.findById(reservation.getBook().getId()));
            borrowedBook.setReservation(reservationService.findById(reservation.getId()));
            this.create(borrowedBook);
        }
    }
    public BorrowedBook returnBook( BorrowedBookDTO borrowedBookDTO){
        logger.info("Running BorrowedBookService.returnBook()");
        BorrowedBook borrowedBook = this.findById(borrowedBookDTO.getId());
        borrowedBook.setReturnedOn(LocalDateTime.now());
        borrowedBook.setStatus(BorrowStatus.RETURNED);
        BorrowedBook savedBorrowedBook = borrowedBookRepository.save(borrowedBook);
        logger.info("Borrowed Book Updated: " +savedBorrowedBook);
        return savedBorrowedBook;
    }

    @Override
    // Partial Update (Patch)
    public BorrowedBook patchUpdate(Long id, BorrowedBookDTO updatedBorrowedBookDTO) {
        logger.info("Running BorrowedBookService.patchUpdate()");

        BorrowedBook borrowedBook = this.findById(id);
        // Update only provided fields
        if (updatedBorrowedBookDTO.getReturnedOn() != null){
            borrowedBook.setReturnedOn(updatedBorrowedBookDTO.getReturnedOn());
            borrowedBook.setStatus(BorrowStatus.RETURNED);
        }
       // if (updatedBorrowedBookDTO.getBorrowedDate() != null) borrowedBook.setBorrowDate(updatedBorrowedBookDTO.getBorrowedDate());
       // if (updatedBorrowedBookDTO.getReturnDate() != null) borrowedBook.setExpectedReturnDate(updatedBorrowedBookDTO.getReturnDate());
       // if (updatedBorrowedBookDTO.getStatus() != null) borrowedBook.setStatus(BorrowStatus.valueOf(updatedBorrowedBookDTO.getStatus()));

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
            BorrowedBook savedBorrowedBook = borrowedBookRepository.save(borrowedBook);
            logger.info("Borrowed Book Updated: " +savedBorrowedBook);
            return savedBorrowedBook;
    }

    @Override
    // Convert BorrowedBook entity to BorrowedBookDTO
    public BorrowedBookDTO convertToDTO(BorrowedBook borrowedBook) {
        logger.info("Converting BorrowedBook entity to BorrowedBookDTO");

        BorrowedBookDTO dto = new BorrowedBookDTO();
        dto.setId(borrowedBook.getId());
        dto.setBorrowedDate(borrowedBook.getBorrowDate());
        dto.setReturnDate(borrowedBook.getExpectedReturnDate());
        dto.setReturnedOn(borrowedBook.getReturnedOn());
        dto.setStatus(borrowedBook.getStatus().name());
        dto.setBookBorrowCost(borrowedBook.getBookBorrowCost());

        // Set only IDs for related entities
        if (borrowedBook.getUser() != null){
            dto.setUserDetails(new UserDTO(borrowedBook.getUser().getId(), borrowedBook.getUser().getName()));
            dto.setUserId(borrowedBook.getUser().getId());
        }
        if (borrowedBook.getFine() != null){
            Fine fine = borrowedBook.getFine();
            FineDTO fineDTO = new FineDTO(fine.getId(),fine.getAmount(),fine.getStatus().toString(), fine.getPaidDate());
            dto.setFineDetails(fineDTO);
            //dto.setFineId(borrowedBook.getFine().getId());}
        }


          if (borrowedBook.getBook() != null) {
              BookDTO booDetails = new BookDTO(borrowedBook.getBook().getId(),borrowedBook.getBook().getTitle());
              dto.setBookDetails(booDetails);
          };

        return dto;
    }

    @Override
    // Convert BorrowedBookDTO to BorrowedBook entity
    public BorrowedBook convertToEntity(BorrowedBookDTO borrowedBookDTO) {
        logger.info("Converting BorrowedBookDTO to BorrowedBook entity");

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBorrowDate(borrowedBookDTO.getBorrowedDate());
        borrowedBook.setExpectedReturnDate(borrowedBookDTO.getReturnDate());
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
