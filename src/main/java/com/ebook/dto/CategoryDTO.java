package com.ebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CategoryDTO {

    private Long id;
    @NotBlank(message = "Category name is mandatory")
    @Size(max = 255, message = "Category name must not exceed 255 characters")
    private String categoryName;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private List<Long> bookIds; // List of book IDs related to this category, instead of Book objects

    public CategoryDTO() {
    }

    public CategoryDTO(String categoryName, String description, List<Long> bookIds) {
        this.categoryName = categoryName;
        this.description = description;
        this.bookIds = bookIds;
    }

    // Getters and setters

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}