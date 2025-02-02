package com.ebook.controller;

import com.ebook.domain.Author;
import com.ebook.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Author>> displayAllAuthors() {
        logger.info("Displaying all authors");
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> displayAuthor(@PathVariable("id") Long id) {
        logger.info("Displaying author with id: " + id);
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        logger.info("Creating a new author");
        return new ResponseEntity<>(authorService.create(author), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        logger.info("Updating author with id: " + id);
        authorService.update(id, author);
        return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        logger.info("Deleting author with id: " + id);
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
