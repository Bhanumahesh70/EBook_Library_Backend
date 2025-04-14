package com.ebook.controller;

import com.ebook.Repository.UserRepository;
import com.ebook.domain.Book;
import com.ebook.domain.BorrowedBook;
import com.ebook.domain.Reservation;
import com.ebook.domain.User;
import com.ebook.dto.BookDTO;
import com.ebook.dto.BorrowedBookDTO;
import com.ebook.dto.ReservationDTO;
import com.ebook.dto.UserDTO;
import com.ebook.service.BookService;
import com.ebook.service.BorrowedBookService;
import com.ebook.service.ReservationService;
import com.ebook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebook/users")
public class UserController extends AbstractController<User, UserDTO,Long> {

    private BorrowedBookService borrowedBookService;
    private UserService userService;
    private UserRepository userRepository;
    private ReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService, BorrowedBookService borrowedBookService, UserRepository userRepository,ReservationService reservationService) {
        super(userService, User.class);
        this.borrowedBookService = borrowedBookService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/{id}/borrowedBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<BorrowedBookDTO>> getBooks(@PathVariable("id") Long id) {
        List<BorrowedBook> borrowedBooks = userService.findById(id).getBorrowedBooks();
        List<BorrowedBookDTO> borrowedBooksDTOs = borrowedBooks.stream().map((borrowedbook) -> borrowedBookService.convertToDTO(borrowedbook)).toList();
        logger.info("Fetched Borrowed books: {}",borrowedBooksDTOs.toString());
        return new ResponseEntity<>(borrowedBooksDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<ReservationDTO>> getReservations(@PathVariable("id") Long id) {
        List<Reservation> reservations = userService.findById(id).getReservations();
        List<ReservationDTO> reservationDTOS = reservations.stream().map((reservation) -> reservationService.convertToDTO(reservation)).toList();
        logger.info("Fetched reseravtions: {}",reservationDTOS.toString());
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/borrowedBooks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<BorrowedBookDTO>> PostBooks(@PathVariable("id") Long id, @RequestBody BorrowedBookDTO borrowedBookDTO) {
        User user = userService.findById(id);
        BorrowedBook borrowedBook = borrowedBookService.convertToEntity(borrowedBookDTO);
        if(borrowedBook!=null) {
            borrowedBookService.create(borrowedBook);
            user.addBorrowedBook(borrowedBook);
        }
        userRepository.save(user);
        logger.info("Added new borrowed Book for user.");
        List<BorrowedBook> borrowedBooks =user.getBorrowedBooks();
        List<BorrowedBookDTO> borrowedBooksDTOs = borrowedBooks.stream().map((borrowedbook) -> borrowedBookService.convertToDTO(borrowedbook)).toList();
        logger.info("Borrowed Books for user {} are :{}",user.getName(),borrowedBooksDTOs.toString());
        return new ResponseEntity<>(borrowedBooksDTOs, HttpStatus.OK);
    }
}
