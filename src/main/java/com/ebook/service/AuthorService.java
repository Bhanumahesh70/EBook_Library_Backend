package com.ebook.service;

import com.ebook.Repository.AuthorRepository;
import com.ebook.controller.AuthorController;
import com.ebook.domain.Author;
import com.ebook.dto.AuthorDTO;
import com.ebook.dto.BookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService{

    private final AuthorRepository authorRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // CRUD Operations
    // read: Get All Authors
    public List<Author> findAll(){
        return authorRepository.findAll();
    }
    public Author findById(Long id){
        return authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id:"+id));
    }

    // READ: Find an author by name
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }

    // Create a new Author
    public Author create(Author author){
        return authorRepository.save(author);
    }

    //DELETE Author

    public void delete(Long id){
        if(!authorRepository.existsById(id)){
            throw new RuntimeException("Author not found with id:"+ id);
        }
        authorRepository.deleteById(id);
    }

    //UPDATE author(Full Update)
    public void update(Long id, Author updatedAuthor){
        Author author = authorRepository.findById(id).orElseThrow(()->new RuntimeException("Author not found with id:"+id));
        author.setBio(updatedAuthor.getBio());
        author.setName(updatedAuthor.getName());
        author.setBirthDate(updatedAuthor.getBirthDate());
        author.setNationality(updatedAuthor.getNationality());
        author.setBooks(updatedAuthor.getBooks());
        authorRepository.save(author);
    }



    // Partial Update Author (only update provided fields)
    public Author patchUpdate(Long id, AuthorDTO updatedAuthorDTO) {
        logger.info("Running AuthorService.patchUpdate()");
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id:" + id));

        // Update only the provided fields in the DTO
        if (updatedAuthorDTO.getName() != null) author.setName(updatedAuthorDTO.getName());
        if (updatedAuthorDTO.getBio() != null) author.setBio(updatedAuthorDTO.getBio());
        if (updatedAuthorDTO.getNationality() != null) author.setNationality(updatedAuthorDTO.getNationality());
        if (updatedAuthorDTO.getBirthDate() != null) author.setBirthDate(updatedAuthorDTO.getBirthDate());

        // If needed, handle the book relation (assuming bookIds are provided)
        // We can update the author's books here if necessary
        // if (updatedAuthorDTO.getBookIds() != null) {
        //     // Logic to update books
        // }

        // Save the partially updated author
        return authorRepository.save(author);
    }
    // Convert Author entity to AuthorDTO
    public AuthorDTO convertToDTO(Author author) {
        logger.info("Converting author entity to author DTO");
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBio(author.getBio());
        dto.setNationality(author.getNationality());
        dto.setBirthDate(author.getBirthDate());
        dto.setBookDetails(author.getBooks().stream().map(book -> new BookDTO(book.getId(), book.getTitle())).toList());
        // If needed, set the list of book IDs
        // dto.setBookIds(author.getBooks() != null ? author.getBooks().stream().map(Book::getId).collect(Collectors.toList()) : null);
        return dto;
    }

    // Convert AuthorDTO to Author entity
    public Author convertToEntity(AuthorDTO authorDTO) {
        logger.info("Converting author DTO to author Entity");
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setBio(authorDTO.getBio());
        author.setNationality(authorDTO.getNationality());
        author.setBirthDate(authorDTO.getBirthDate());
        // If needed, set the list of books or any other fields
        // You may use the list of book IDs to set the books if needed
        // author.setBooks(bookRepository.findAllById(authorDTO.getBookIds()));
        return author;
    }
}



