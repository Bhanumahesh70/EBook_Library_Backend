package com.ebook.controller;

import com.ebook.domain.BorrowedBook;
import com.ebook.service.BorrowedBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowed-books")
public class BorrowedBookController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowedBookController.class);

    private final BorrowedBookService borrowedBookService;

    @Autowired
    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BorrowedBook>> displayAllBorrowedBooks() {
        logger.info("Displaying all borrowed books");
        return new ResponseEntity<>(borrowedBookService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BorrowedBook> displayBorrowedBook(@PathVariable("id") Long id) {
        logger.info("Displaying borrowed book with id: " + id);
        return new ResponseEntity<>(borrowedBookService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BorrowedBook> createBorrowedBook(@RequestBody BorrowedBook borrowedBook) {
        logger.info("Creating a new borrowed book entry");
        return new ResponseEntity<>(borrowedBookService.create(borrowedBook), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BorrowedBook> updateBorrowedBook(@PathVariable Long id, @RequestBody BorrowedBook borrowedBook) {
        logger.info("Updating borrowed book with id: " + id);
        borrowedBookService.update(id, borrowedBook);
        return new ResponseEntity<>(borrowedBookService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowedBook(@PathVariable Long id) {
        logger.info("Deleting borrowed book with id: " + id);
        borrowedBookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
