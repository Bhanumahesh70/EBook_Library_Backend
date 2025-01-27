package com.ebook.service;

import Repository.PublisherRepository;
import com.ebook.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService extends AbstractCRUDService<Publisher,Long>{

    private final PublisherRepository publisherRepository;

    public PublisherService(JpaRepository<Publisher, Long> repository, PublisherRepository publisherRepository) {
        super(repository);
        this.publisherRepository = publisherRepository;
    }
}
