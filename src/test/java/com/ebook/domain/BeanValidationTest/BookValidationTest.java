package com.ebook.domain.BeanValidationTest;
import com.ebook.domain.Book;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;
public class BookValidationTest extends AbstractBeanValidationTest{

    @Test
    public void validateTitleNotBlank() {
        // Invalid Book (Title is blank)
        Book book = new Book(2020, 5, 10, "English", "", "Author Name", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to blank title.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("title".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Title is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateAuthorNotBlank() {
        // Invalid Book (Author is blank)
        Book book = new Book(2020, 5, 10, "English", "Book Title", "", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to blank author.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("author".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Author is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateIsbnPattern() {
        // Invalid Book (ISBN is not a 13-digit number)
        Book book = new Book(2020, 5, 10, "English", "Book Title", "Author Name", "123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to invalid ISBN.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("isbn".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("ISBN must be a 13-digit number", violation.getMessage());
            }
        }
    }

    @Test
    public void validateLanguageNotBlank() {
        // Invalid Book (Language is blank)
        Book book = new Book(2020, 5, 10, "", "Book Title", "Author Name", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to blank language.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("language".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Language is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateTotalCopiesMinValue() {
        // Invalid Book (Total copies less than 1)
        Book book = new Book(2020, 5, 0, "English", "Book Title", "Author Name", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to total copies being less than 1.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("totalCopies".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Total copies must be at least 1", violation.getMessage());
            }
        }
    }

    @Test
    public void validateAvailableCopiesMinValue() {
        // Invalid Book (Available copies negative)
        Book book = new Book(2020, -1, 10, "English", "Book Title", "Author Name", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to negative available copies.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("availableCopies".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Available copies cannot be negative", violation.getMessage());
            }
        }
    }

    @Test
    public void validatePublicationYearRange() {
        // Invalid Book (Publication year out of range)
        Book book = new Book(999, 5, 10, "English", "Book Title", "Author Name", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to invalid publication year.");

        // Assert the correct violation message
        for (ConstraintViolation<Book> violation : violations) {
            if ("publicationYear".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Publication year must be a valid year", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidBook() {
        // Valid Book
        Book book = new Book(2020, 5, 10, "English", "Book Title", "Author Name", "1234567890123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }
        // There should be no violations for a valid book
        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for valid book.");
    }
}
