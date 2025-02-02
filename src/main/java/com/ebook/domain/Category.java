package com.ebook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
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
   // @JsonBackReference("book-categories")
    @JsonIgnore
    private List<Book> books;

    public Category() {
    }

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    /**
     * Entity RelationShips methods
     */
    public void addBook(Book book){
        if(book==null){
            return;
        }
        if(this.books==null){
            this.books = new ArrayList<Book>();
        }
        if(!this.books.contains(book)){
            this.books.add(book);
            book.addCategory(this);
        }

    }
    public void removeBook(Book book) {
        if(book==null || this.books ==null){
            return;
        }
        if (this.books.contains(book)) {
            this.books.remove(book);
            book.removeCategory(this);
        }
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
        if(this.books == books){
            return;
        }
        if(this.books!=null){
            for(Book book: this.books){
                book.removeCategory(this);
            }
        }
        if(books!=null){
            for(Book book:books){
                book.addCategory(this);
            }
        }
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
