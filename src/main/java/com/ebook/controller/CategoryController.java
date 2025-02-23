package com.ebook.controller;

import com.ebook.domain.Category;
import com.ebook.dto.CategoryDTO;
import com.ebook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController extends AbstractController<Category, CategoryDTO, Long> {

    @Autowired
    public CategoryController(CategoryService categoryService) {
        super(categoryService, Category.class);
    }
}
