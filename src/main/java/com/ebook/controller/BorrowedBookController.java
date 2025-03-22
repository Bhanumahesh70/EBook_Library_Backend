package com.ebook.controller;

import com.ebook.domain.BorrowedBook;
import com.ebook.dto.BorrowedBookDTO;
import com.ebook.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebook/borrowedBooks")
public class BorrowedBookController extends AbstractController<BorrowedBook, BorrowedBookDTO,Long> {

    @Autowired
    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        super(borrowedBookService, BorrowedBook.class);
    }
}