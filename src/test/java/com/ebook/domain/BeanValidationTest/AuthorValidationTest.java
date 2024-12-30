package com.ebook.domain.BeanValidationTest;

import com.ebook.domain.Author;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

public class AuthorValidationTest extends AbstractBeanValidationTest{



        @Test
        public void validateNameNotBlank() {
            // Invalid Author (Name is blank)
            Author author = new Author("", "Author bio", "Nationality", LocalDate.of(1980, 1, 1));

            Set<ConstraintViolation<Author>> violations = validator.validate(author);

            Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to blank name.");

            // Assert the correct violation message
            for (ConstraintViolation<Author> violation : violations) {
                if ("name".equals(violation.getPropertyPath().toString())) {
                    Assertions.assertEquals("Name is mandatory", violation.getMessage());
                }
            }
        }

        @Test
        public void validateBioMaxLength() {
            // Invalid Author (Bio exceeds max length)
            String longBio = "A".repeat(1001); // 1001 characters
            Author author = new Author("Author Name", longBio, "Nationality", LocalDate.of(1980, 1, 1));

            Set<ConstraintViolation<Author>> violations = validator.validate(author);

            Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to bio exceeding max length.");

            // Assert the correct violation message
            for (ConstraintViolation<Author> violation : violations) {
                if ("bio".equals(violation.getPropertyPath().toString())) {
                    Assertions.assertEquals("Bio must not exceed 1000 characters", violation.getMessage());
                }
            }
        }

        @Test
        public void validateNationalityMaxLength() {
            // Invalid Author (Nationality exceeds max length)
            String longNationality = "A".repeat(51); // 51 characters
            Author author = new Author("Author Name", "Bio", longNationality, LocalDate.of(1980, 1, 1));

            Set<ConstraintViolation<Author>> violations = validator.validate(author);

            Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to nationality exceeding max length.");

            // Assert the correct violation message
            for (ConstraintViolation<Author> violation : violations) {
                if ("nationality".equals(violation.getPropertyPath().toString())) {
                    Assertions.assertEquals("Nationality must not exceed 50 characters", violation.getMessage());
                }
            }
        }

        @Test
        public void validateBirthDateInPast() {
            // Invalid Author (Birth date is in the future)
            Author author = new Author("Author Name", "Bio", "Nationality", LocalDate.now().plusDays(1));

            Set<ConstraintViolation<Author>> violations = validator.validate(author);

            Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to birth date being in the future.");

            // Assert the correct violation message
            for (ConstraintViolation<Author> violation : violations) {
                if ("birthDate".equals(violation.getPropertyPath().toString())) {
                    Assertions.assertEquals("Birth date must be in the past", violation.getMessage());
                }
            }
        }

        @Test
        public void validateValidAuthor() {
            // Valid Author
            Author author = new Author("Author Name", "Bio", "Nationality", LocalDate.of(1980, 1, 1));

            Set<ConstraintViolation<Author>> violations = validator.validate(author);

            if (!violations.isEmpty()) {
                violations.forEach(violation -> System.out.println(
                        "Violation: Property - " + violation.getPropertyPath() +
                                ", Message - " + violation.getMessage()));
            }
            // There should be no violations for a valid author
            Assertions.assertTrue(violations.isEmpty(), "Validation should pass for valid author.");
        }
}
