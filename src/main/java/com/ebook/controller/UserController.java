package com.ebook.controller;

import com.ebook.Repository.UserRepository;
import com.ebook.domain.*;
import com.ebook.dto.*;
import com.ebook.service.*;
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

    private final BorrowedBookService borrowedBookService;
    private final UserService userService;
    private  final UserRepository userRepository;
    private final ReservationService reservationService;
    private final FineService fineService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService, BorrowedBookService borrowedBookService, UserRepository userRepository,ReservationService reservationService,FineService fineService) {
        super(userService, User.class);
        this.borrowedBookService = borrowedBookService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
        this.fineService = fineService;
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
        List<ReservationDTO> reservationDTOS = reservations.stream().map(reservationService::convertToDTO).toList();
        logger.info("Fetched reseravtions: {}",reservationDTOS.toString());
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/fines", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<FineDTO>> getFines(@PathVariable("id") Long id) {
        List<Fine> fines = userService.findById(id).getFines();
        List<FineDTO> fineDTOS = fines.stream().map(fineService::convertToDTO).toList();
        logger.info("Fetched fines: {}",fineDTOS.toString());
        return new ResponseEntity<>(fineDTOS, HttpStatus.OK);
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
