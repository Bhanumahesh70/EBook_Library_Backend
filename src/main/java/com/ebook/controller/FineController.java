package com.ebook.controller;

import com.ebook.domain.Fine;
import com.ebook.dto.FineDTO;
import com.ebook.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebook/fines")
public class FineController extends AbstractController<Fine, FineDTO,Long> {

    @Autowired
    public FineController(FineService fineService) {
        super(fineService, Fine.class);
    }
}
