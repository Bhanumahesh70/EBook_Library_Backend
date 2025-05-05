package com.ebook.controller;

import com.ebook.domain.Author;
import com.ebook.domain.BorrowedBook;
import com.ebook.dto.AuthorDTO;
import com.ebook.dto.BorrowedBookDTO;
import com.ebook.service.BorrowedBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebook/borrowedBooks")
public class BorrowedBookController extends AbstractController<BorrowedBook, BorrowedBookDTO,Long> {
    private static final Logger logger = LoggerFactory.getLogger(BorrowedBookController.class);

    private BorrowedBookService borrowedBookService;
    @Autowired
    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        super(borrowedBookService, BorrowedBook.class);
        this.borrowedBookService = borrowedBookService;
    }


    @PostMapping(value="/returnBook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BorrowedBookDTO> createAuthor(@RequestBody BorrowedBookDTO borrowedBookDTO) {
        logger.info("Returning borrowed book");
        BorrowedBook returnedBorrowedBook = borrowedBookService.returnBook(borrowedBookDTO);
        return new ResponseEntity<>(borrowedBookService.convertToDTO(returnedBorrowedBook), HttpStatus.CREATED);
    }
}