package com.ebook.controller;

import com.ebook.domain.Author;
import com.ebook.domain.Book;
import com.ebook.dto.AuthorDTO;
import com.ebook.dto.BookDTO;
import com.ebook.service.AuthorService;
import com.ebook.service.BookService;
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
@RequestMapping("/ebook/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;
    private BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService,BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDTO>> displayAllAuthors() {
        logger.info("Displaying all authors");
        List<Author> authors = authorService.findAll();
        List<AuthorDTO> authorDTOS = authors.stream().map(authorService::convertToDTO).toList();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> displayAuthor(@PathVariable("id") String id) {
        logger.info("Displaying author with id: " + id);
        Author author = authorService.findById(Long.parseLong(id));
        AuthorDTO authorDTO = authorService.convertToDTO(author);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<BookDTO>>getBooks(@PathVariable("id") Long id){
        List<Book> books = authorService.findById(id).getBooks();
        List<BookDTO> booksDTOs = books.stream().map((book)-> bookService.convertToDTO(book)).toList();
        return new ResponseEntity<>(booksDTOs, HttpStatus.OK);
    }
    // Create a new Author (Accepting AuthorDTO)
    @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        logger.info("Creating a new author");
        Author author = authorService.convertToEntity(authorDTO);
        Author createdAuthor = authorService.create(author);
        return new ResponseEntity<>(authorService.convertToDTO(createdAuthor), HttpStatus.CREATED);
    }
    // Full Update Author (Accepting AuthorDTO)
    @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        logger.info("Updating author with id: " + id);
        authorService.update(id, author);
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }
    // Partial Update Author (Accepting AuthorDTO)
    @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> patchUpdateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        logger.info("Patch Request Updating author with id: " + id);
        Author updatedAuthor = authorService.patchUpdate(id,authorDTO);
        return new ResponseEntity<>(authorService.convertToDTO(updatedAuthor), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')" )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        logger.info("Deleting author with id: " + id);
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
