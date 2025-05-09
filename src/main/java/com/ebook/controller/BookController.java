package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.dto.BookDTO;
import com.ebook.service.BookService;
import io.jsonwebtoken.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

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

    @PostMapping(value = "/{id}/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadBookImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException, java.io.IOException {
        Book book = bookService.findById(id);
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get("uploads", filename);

        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        book.setCoverImagePath(filename);
        bookService.update(id, book);
        logger.info("Book Image Path: {}", book.getCoverImagePath());

        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping("/cover/{filename}")
    public ResponseEntity<Resource> getCoverImage(@PathVariable String filename) throws IOException, MalformedURLException {
        Path filePath = Paths.get("uploads", filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }


//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Book>> displayAllEntities() {
//        logger.info("Displaying all {}", "Books in bookController");
//        List<Book> books = bookService.findAll();
//       // List<BookDTO> entityDTOs = entities.stream().map(abstractService::convertToDTO).toList();
//        return new ResponseEntity<>(books, HttpStatus.OK);
//    }
}
