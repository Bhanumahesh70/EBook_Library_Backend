package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("")
    public List<Book> diplayAllBooks(){
        return bookService.findAll();
    }
}
