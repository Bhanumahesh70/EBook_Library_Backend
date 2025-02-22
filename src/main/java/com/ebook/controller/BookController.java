package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.dto.BookDTO;
import com.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController extends AbstractController<Book,Long> {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
      super(bookService,Book.class);
      this.bookService = bookService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        Book updatedBook =  bookService.updatePartialBook(id, updatedBookDTO);
        return new ResponseEntity<>(bookService.convertToDTO(updatedBook), HttpStatus.OK);
    }
}
