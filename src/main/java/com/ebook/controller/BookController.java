package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.dto.BookDTO;
import com.ebook.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebook/books")
public class BookController extends AbstractController<Book,BookDTO,Long> {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
      super(bookService,Book.class);
      this.bookService = bookService;
    }


//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Book>> displayAllEntities() {
//        logger.info("Displaying all {}", "Books in bookController");
//        List<Book> books = bookService.findAll();
//       // List<BookDTO> entityDTOs = entities.stream().map(abstractService::convertToDTO).toList();
//        return new ResponseEntity<>(books, HttpStatus.OK);
//    }
}
