package com.ebook.service;

import Repository.AuthorRepository;
import com.ebook.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // CRUD Operations
    // read: Get All Authors
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }
    public Author findAuthorByID(Long id){
        return authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id:"+id));
    }

    // READ: Find an author by name
    public Author findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    // Create a new Author
    public void createAuthor(Author author){
        authorRepository.save(author);
    }

    //DELETE Author

    public void deleteAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new RuntimeException("Author not found with id:"+ id);
        }
        authorRepository.deleteById(id);
    }

    //UPDATE author

    public void updateAuthor(Long id, Author updatedAuthor){
        Author author = authorRepository.findById(id).orElseThrow(()->new RuntimeException("Author not found with id:"+id));
        author.setBio(updatedAuthor.getBio());
        author.setName(updatedAuthor.getName());
        author.setBirthDate(updatedAuthor.getBirthDate());
        author.setNationality(updatedAuthor.getNationality());
        author.setBooks(updatedAuthor.getBooks());
        authorRepository.save(author);
    }
}
