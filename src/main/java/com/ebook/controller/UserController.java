package com.ebook.controller;

import com.ebook.domain.Book;
import com.ebook.domain.BorrowedBook;
import com.ebook.domain.User;
import com.ebook.dto.BookDTO;
import com.ebook.dto.BorrowedBookDTO;
import com.ebook.dto.UserDTO;
import com.ebook.service.BookService;
import com.ebook.service.BorrowedBookService;
import com.ebook.service.UserService;
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
@RequestMapping("/ebook/users")
public class UserController extends AbstractController<User, UserDTO,Long> {

    private BorrowedBookService borrowedBookService;
    private UserService userService;
    @Autowired
    public UserController(UserService userService, BorrowedBookService borrowedBookService) {
        super(userService, User.class);
        this.borrowedBookService = borrowedBookService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}/borrowedBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<BorrowedBookDTO>> getBooks(@PathVariable("id") Long id){
        List<BorrowedBook> borrowedBooks = userService.findById(id).getBorrowedBooks();
        List<BorrowedBookDTO> borrowedBooksDTOs = borrowedBooks.stream().map((borrowedbook)-> borrowedBookService.convertToDTO(borrowedbook)).toList();
        return new ResponseEntity<>(borrowedBooksDTOs, HttpStatus.OK);
    }
}
