package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.domain.Category;
import com.ebook.dto.BookDTO;
import com.ebook.dto.CategoryDTO;
import com.ebook.service.AbstractCRUDService;
import com.ebook.service.BookService;
import com.ebook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook/categories")
public class CategoryController extends AbstractController<Category, CategoryDTO, Long> {

    private CategoryService categoryService;
    private  BookService bookService;

    @Autowired
    public CategoryController(CategoryService categoryService, BookService bookService) {
        super(categoryService, Category.class);
        this.categoryService = categoryService;
        this.bookService = bookService;
    }


    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<BookDTO>>getBooks(@PathVariable("id") Long id){
       List<Book> books = categoryService.findById(id).getBooks();
       List<BookDTO> booksDTOs = books.stream().map((book)-> bookService.convertToDTO(book)).toList();
       return new ResponseEntity<>(booksDTOs, HttpStatus.OK);
    }
}
