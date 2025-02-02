package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
//    @GetMapping("")
//    public String diplayAllBooks(){
//        List<Book> books= bookService.findAll();
//        String output ="";
//        for(Book book:books){
//            output+="\n "+book.toString();
//        }
//        return output;
//    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> diplayAllBooks(){
        logger.info("Displaying All Books");
        return bookService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book displayBook(@PathVariable("id") Long id){
        logger.info("Dipslaing book with id: "+id);
        return bookService.findById(id);
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book createBook(@RequestBody Book book){
        logger.info("creating a new book");
        return bookService.create(book);
    }

    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book updateBook(@PathVariable Long id, @RequestBody Book book){
        logger.info("Updating book with id: "+id);
         bookService.update(id, book);
         return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        logger.info("Deleting book with id: "+id);
        bookService.delete(id);
    }
}
