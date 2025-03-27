package com.ebook.dto;

import com.ebook.domain.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    private String isbn;

    @NotBlank(message = "Language is mandatory")
    private String language;

    @Min(value = 1, message = "Total copies must be at least 1")
    private int totalCopies;

    @Min(value = 0, message = "Available copies cannot be negative")
    private int availableCopies;

    @Min(value = 1000, message = "Publication year must be a valid year")
    @Max(value = 2025, message = "Publication year must be a valid year")
    private int publicationYear;

    private List<Long> authorIds;  // List of author IDs for the book
    private Long publisherId;      // Publisher ID for the book
    private List<Long> categoryIds; // List of category IDs associated with the book
   // private List<CategoryDTO> categoriesDTO;

    public BookDTO() {
    }

    public BookDTO(String title, String author, String isbn, String language, int totalCopies, int availableCopies, int publicationYear, List<Long> authorIds, Long publisherId,List<Long> categoryIds) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.language = language;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.publicationYear = publicationYear;
        this.authorIds = authorIds;
        this.publisherId = publisherId;
        this.categoryIds = categoryIds;
        //this.categoriesDTO = categoriesDTOs;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

//    public List<CategoryDTO> getCategoriesDTO() {
//        return categoriesDTO;
//    }
//
//    public void setCategoriesDTO(List<CategoryDTO> categoriesDTO) {
//        this.categoriesDTO = categoriesDTO;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}