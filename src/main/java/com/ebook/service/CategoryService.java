package com.ebook.service;

import Repository.CategoryRepository;
import com.ebook.domain.BorrowedBook;
import com.ebook.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends AbstractCRUDService<Category,Long>{

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository );
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void update(Long id, Category updatedCategory) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);

        if (existingCategoryOpt.isPresent()) {
            Category existingCategory = existingCategoryOpt.get();

            // Update fields
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            existingCategory.setDescription(updatedCategory.getDescription());
            existingCategory.setBooks(updatedCategory.getBooks());

            // Save updated entity
            categoryRepository.save(existingCategory);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
    }
}
