package com.ebook.domain.BeanValidationTest;
import com.ebook.domain.Book;
import com.ebook.domain.Category;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;
public class CategoryValidationTest extends AbstractBeanValidationTest{
    @Test
    public void validateCategoryNameNotBlank() {
        // Invalid Category (Category name is blank)
        Category category = new Category("", "A valid description.");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to blank category name.");

        // Assert the correct violation message
        for (ConstraintViolation<Category> violation : violations) {
            if ("categoryName".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Category name is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateCategoryNameMaxSize() {
        // Invalid Category (Category name exceeds 255 characters)
        String longName = "A".repeat(256);
        Category category = new Category(longName, "A valid description.");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to category name exceeding 255 characters.");

        // Assert the correct violation message
        for (ConstraintViolation<Category> violation : violations) {
            if ("categoryName".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Category name must not exceed 255 characters", violation.getMessage());
            }
        }
    }

    @Test
    public void validateDescriptionMaxSize() {
        // Invalid Category (Description exceeds 2000 characters)
        String longDescription = "A".repeat(2001);
        Category category = new Category("Valid Category", longDescription);

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to description exceeding 2000 characters.");

        // Assert the correct violation message
        for (ConstraintViolation<Category> violation : violations) {
            if ("description".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Description must not exceed 2000 characters", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidCategory() {
        // Valid Category
        Category category = new Category("Fiction", "A category for fictional books.");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }

        // There should be no violations for a valid category
        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for a valid category.");
    }

    @Test
    public void validateDescriptionOptional() {
        // Valid Category (Description is null, which is allowed)
        Category category = new Category("Non-Fiction", null);

        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        Assertions.assertTrue(violations.isEmpty(), "Validation should pass when description is null.");
    }


}

