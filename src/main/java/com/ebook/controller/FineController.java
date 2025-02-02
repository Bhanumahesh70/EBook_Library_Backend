package com.ebook.controller;

import com.ebook.domain.Fine;
import com.ebook.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fines")
public class FineController extends AbstractController<Fine, Long> {

    @Autowired
    public FineController(FineService fineService) {
        super(fineService, Fine.class);
    }
}
