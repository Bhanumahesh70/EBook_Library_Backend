package com.ebook.controller;

import com.ebook.domain.Book;
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
@RequestMapping("/books")
public class BookController extends AbstractController<Book,Long> {

    @Autowired
    public BookController(BookService bookService) {
      super(bookService,Book.class);
    }
}
