package com.ebook.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Category")
@NamedQuery(name="Category.findAll",query="select c from Category c")
public class Category extends AbstractClass{

    @Column(name = "category_name", nullable = false, unique = true)
    @NotBlank(message = "Category name is mandatory")
    @Size(max = 255, message = "Category name must not exceed 255 characters")
    private String categoryName;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    /**
     * Entity RelationShips
     */
    @ManyToMany
    @JoinTable(name = "Category_Book",
            joinColumns = @JoinColumn(name = "categoryId"),
            inverseJoinColumns = @JoinColumn(name = "BookId")
    )
    private List<Book> books;

    public Category() {
    }

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }

    /**
     * Getters and Setters
     * @return
     */

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }
}
