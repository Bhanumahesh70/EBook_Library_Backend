package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController extends AbstractController<Book,Long> {

    @Autowired
    public BookController(BookService bookService) {
      super(bookService,Book.class);
    }
}
