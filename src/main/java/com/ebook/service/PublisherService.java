package com.ebook.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import com.ebook.Repository.BookRepository;
import com.ebook.Repository.PublisherRepository;
import com.ebook.domain.Book;
import com.ebook.domain.Publisher;
import com.ebook.dto.PublisherDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService extends AbstractCRUDService<Publisher,PublisherDTO,Long>{

    private static final Logger logger =  LoggerFactory.getLogger(PublisherService.class);

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    @Value("${server.url:http://localhost:8080}")
    private String serverUrl;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        super(publisherRepository);
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }


    // Partial Update (Patch)
    @Override
    public Publisher patchUpdate(Long id, PublisherDTO updatedPublisherDTO) {
        logger.info("Running PublisherService.patchUpdate()");

        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));

        // Update only provided fields
        if (updatedPublisherDTO.getName() != null) publisher.setName(updatedPublisherDTO.getName());
        if (updatedPublisherDTO.getAddress() != null) publisher.setAddress(updatedPublisherDTO.getAddress());
        if (updatedPublisherDTO.getEmail() != null) publisher.setEmail(updatedPublisherDTO.getEmail());
        if (updatedPublisherDTO.getPhoneNumber() != null) publisher.setPhoneNumber(updatedPublisherDTO.getPhoneNumber());

        /*
        // Handle book relationships
        if (updatedPublisherDTO.getBookIds() != null) {
            List<Book> books = updatedPublisherDTO.getBookIds().stream()
                    .map(bookId -> bookRepository.findById(bookId)
                            .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId)))
                    .collect(Collectors.toList());
            publisher.setBooks(books);
        }
*/
        return publisherRepository.save(publisher);
    }

    public Publisher addPublisherImage(Long id, MultipartFile file) {
        try {
            Publisher publisher = this.findById(id);

            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads", filename);

            // Ensure the directory exists
            Files.createDirectories(filePath.getParent());

            // Copy file to the target location
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set the path and save the publisher
            publisher.setCoverImagePath(filename);
            logger.info("publisher Image Path: {}", publisher.getCoverImagePath());

            return publisherRepository.save(publisher);

        } catch (IOException e) {
            logger.error("Failed to store file for publisher ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to upload cover image for publisher ID " + id, e);
        } catch (Exception e) {
            logger.error("Unexpected error while uploading publisher image for ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unexpected error during publisher image upload", e);
        }
    }
    // Convert Publisher entity to PublisherDTO
    @Override
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
        dto.setCoverImageUrl(serverUrl + "/ebook/books/cover/" + publisher.getCoverImagePath());
        return dto;
    }

    // Convert PublisherDTO to Publisher entity
    @Override
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
