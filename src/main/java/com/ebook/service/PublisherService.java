package com.ebook.service;
import java.util.List;
import java.util.logging.Logger;

import com.ebook.Repository.BookRepository;
import com.ebook.Repository.PublisherRepository;
import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import com.ebook.dto.PublisherDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService extends AbstractCRUDService<Publisher,Long>{

    private static final Logger logger = Logger.getLogger(PublisherService.class.getName());

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        super(publisherRepository);
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }


    // Partial Update (Patch)
    public Publisher patchUpdate(Long id, PublisherDTO updatedPublisherDTO) {
        logger.info("Running PublisherService.patchUpdate()");

        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));

        // Update only provided fields
        if (updatedPublisherDTO.getName() != null) publisher.setName(updatedPublisherDTO.getName());
        if (updatedPublisherDTO.getAddress() != null) publisher.setAddress(updatedPublisherDTO.getAddress());
        if (updatedPublisherDTO.getEmail() != null) publisher.setEmail(updatedPublisherDTO.getEmail());
        if (updatedPublisherDTO.getPhoneNumber() != null) publisher.setPhoneNumber(updatedPublisherDTO.getPhoneNumber());

        // Handle book relationships
        if (updatedPublisherDTO.getBookIds() != null) {
            List<Book> books = updatedPublisherDTO.getBookIds().stream()
                    .map(bookId -> bookRepository.findById(bookId)
                            .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId)))
                    .collect(Collectors.toList());
            publisher.setBooks(books);
        }

        return publisherRepository.save(publisher);
    }

    // Convert Publisher entity to PublisherDTO
    public PublisherDTO convertToDTO(Publisher publisher) {
        logger.info("Converting Publisher entity to PublisherDTO");

        PublisherDTO dto = new PublisherDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setAddress(publisher.getAddress());
        dto.setEmail(publisher.getEmail());
        dto.setPhoneNumber(publisher.getPhoneNumber());

        // Only store book IDs
        dto.setBookIds(publisher.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList()));

        return dto;
    }

    // Convert PublisherDTO to Publisher entity
    public Publisher convertToEntity(PublisherDTO publisherDTO) {
        logger.info("Converting PublisherDTO to Publisher entity");

        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisher.setAddress(publisherDTO.getAddress());
        publisher.setEmail(publisherDTO.getEmail());
        publisher.setPhoneNumber(publisherDTO.getPhoneNumber());

        // Set books if IDs are provided
        if (publisherDTO.getBookIds() != null) {
            List<Book> books = publisherDTO.getBookIds().stream()
                    .map(bookId -> bookRepository.findById(bookId)
                            .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId)))
                    .collect(Collectors.toList());
            publisher.setBooks(books);
        }

        return publisher;
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
