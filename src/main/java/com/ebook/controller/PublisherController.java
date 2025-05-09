package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import com.ebook.dto.BookDTO;
import com.ebook.dto.PublisherDTO;
import com.ebook.service.BookService;
import com.ebook.service.PublisherService;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/ebook/publishers")
public class PublisherController extends AbstractController<Publisher, PublisherDTO,Long> {

    private PublisherService publisherService;
    private BookService bookService;
    @Autowired
    public PublisherController(PublisherService publisherService, BookService bookService) {
        super(publisherService, Publisher.class);
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<BookDTO>> getBooks(@PathVariable("id") Long id){
        List<Book> books = publisherService.findById(id).getBooks();
        List<BookDTO> booksDTOs = books.stream().map((book)-> bookService.convertToDTO(book)).toList();
        return new ResponseEntity<>(booksDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Publisher> uploadPublisherImage(@PathVariable Long id, @RequestParam("file") MultipartFile file)  {
        return ResponseEntity.ok(publisherService.addPublisherImage(id,file));
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
}
