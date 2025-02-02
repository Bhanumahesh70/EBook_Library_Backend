package com.ebook.controller;

import com.ebook.domain.Publisher;
import com.ebook.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publishers")
public class PublisherController extends AbstractController<Publisher, Long> {

    @Autowired
    public PublisherController(PublisherService publisherService) {
        super(publisherService, Publisher.class);
    }
}
