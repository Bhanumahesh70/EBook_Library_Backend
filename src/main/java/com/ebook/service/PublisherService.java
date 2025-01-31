package com.ebook.service;

import com.ebook.Repository.PublisherRepository;
import com.ebook.domain.Publisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService extends AbstractCRUDService<Publisher,Long>{

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(JpaRepository<Publisher, Long> repository, PublisherRepository publisherRepository) {
        super(repository);
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
        Optional<Publisher> existingPublisherOptional = publisherRepository.findById(id);

        if (existingPublisherOptional.isPresent()) {
            Publisher existingPublisher = existingPublisherOptional.get();

            // Update fields
            existingPublisher.setName(updatedPublisher.getName());
            existingPublisher.setAddress(updatedPublisher.getAddress());
            existingPublisher.setEmail(updatedPublisher.getEmail());
            existingPublisher.setPhoneNumber(updatedPublisher.getPhoneNumber());

            // Handle books relationship
            if (updatedPublisher.getBooks() != null) {
                existingPublisher.setBooks(updatedPublisher.getBooks());
            }

            // Save and return updated publisher
            return publisherRepository.save(existingPublisher);
        } else {
            throw new RuntimeException("Publisher not found with ID: " + id);
        }
    }
}
