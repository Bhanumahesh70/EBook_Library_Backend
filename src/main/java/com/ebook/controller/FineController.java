package com.ebook.controller;

import com.ebook.domain.BorrowedBook;
import com.ebook.domain.Fine;
import com.ebook.dto.BorrowedBookDTO;
import com.ebook.dto.FineDTO;
import com.ebook.service.FineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebook/fines")
public class FineController extends AbstractController<Fine, FineDTO,Long> {

    private static final Logger logger = LoggerFactory.getLogger(FineController.class);
    private FineService fineService;
    @Autowired
    public FineController(FineService fineService) {
        super(fineService, Fine.class);
        this.fineService = fineService;
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value="/payFine", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FineDTO> createAuthor(@RequestBody FineDTO fineId) {
        logger.info("Paying fine");
        Fine paidFine = fineService.payFine(fineId);
        return new ResponseEntity<>(fineService.convertToDTO(paidFine), HttpStatus.CREATED);
    }
}
