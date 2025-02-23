package com.ebook.service;
import com.ebook.domain.Book;
import com.ebook.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.ebook.Repository.BookRepository;
import com.ebook.Repository.CategoryRepository;
import com.ebook.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CategoryService extends AbstractCRUDService<Category,CategoryDTO,Long>{

    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }
    // Partial Update (Patch)
    @Override
    public Category patchUpdate(Long id, CategoryDTO updatedCategoryDTO) {
        logger.info("Running CategoryService.patchUpdate()");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        // Update only provided fields
        if (updatedCategoryDTO.getCategoryName() != null) category.setCategoryName(updatedCategoryDTO.getCategoryName());
        if (updatedCategoryDTO.getDescription() != null) category.setDescription(updatedCategoryDTO.getDescription());

        // Handle book relation (if bookIds are provided)
        if (updatedCategoryDTO.getBookIds() != null) {
            List<Book> books = bookRepository.findAllById(updatedCategoryDTO.getBookIds());
            category.setBooks(books);
        }

        return categoryRepository.save(category);
    }

    // Convert Category entity to CategoryDTO
    @Override
    public CategoryDTO convertToDTO(Category category) {
        logger.info("Converting Category entity to CategoryDTO");

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());

        // Set only book IDs instead of full book objects
        if (category.getBooks() != null) {
            dto.setBookIds(category.getBooks().stream().map(Book::getId).collect(Collectors.toList()));
        }

        return dto;
    }

    // Convert CategoryDTO to Category entity
    @Override
    public Category convertToEntity(CategoryDTO categoryDTO) {
        logger.info("Converting CategoryDTO to Category entity");

        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());

        // Set books if bookIds are provided
        if (categoryDTO.getBookIds() != null) {
            List<Book> books = bookRepository.findAllById(categoryDTO.getBookIds());
            category.setBooks(books);
        }

        return category;
    }
    @Override
    public Category update(Long id, Category updatedCategory) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);

        if (existingCategoryOpt.isPresent()) {
            Category existingCategory = existingCategoryOpt.get();

            // Update fields
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            existingCategory.setDescription(updatedCategory.getDescription());
            existingCategory.setBooks(updatedCategory.getBooks());

            // Save updated entity
           return categoryRepository.save(existingCategory);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
    }
}
