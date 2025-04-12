package com.ebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class AuthorDTO {

    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;

    @Size(max = 50, message = "Nationality must not exceed 50 characters")
    private String nationality;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

   // private List<Long> bookIds; // List of book IDs authored by the author
    private List<BookDTO> bookDetails;

    public AuthorDTO() {
    }

    public AuthorDTO(String name, String bio, String nationality, LocalDate birthDate,List<BookDTO> bookDetails) {
        this.name = name;
        this.bio = bio;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.bookDetails = bookDetails;
    }
public AuthorDTO(Long id, String name){
        this.id = id;
        this.name = name;
}
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<BookDTO> getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(List<BookDTO> bookDetails) {
        this.bookDetails = bookDetails;
    }

//    public List<Long> getBookIds() {
//        return bookIds;
//    }
//
//    public void setBookIds(List<Long> bookIds) {
//        this.bookIds = bookIds;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}