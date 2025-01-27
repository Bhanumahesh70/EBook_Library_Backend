package com.ebook.service;

import Repository.CategoryRepository;
import com.ebook.domain.BorrowedBook;
import com.ebook.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCRUDService<Category,Long>{

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository );
        this.categoryRepository = categoryRepository;
    }

}
