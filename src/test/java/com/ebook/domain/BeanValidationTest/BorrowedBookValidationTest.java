package com.ebook.domain.BeanValidationTest;
import com.ebook.domain.BorrowedBook;
import com.ebook.domain.BorrowStatus;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
public class BorrowedBookValidationTest extends AbstractBeanValidationTest{
    @Test
    public void validateBorrowDateNotNull() {
        // Invalid BorrowedBook (Borrow date is null)
        BorrowedBook borrowedBook = new BorrowedBook(null, LocalDateTime.now().plusDays(1), null, BorrowStatus.BORROWED);

        Set<ConstraintViolation<BorrowedBook>> violations = validator.validate(borrowedBook);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null borrow date.");

        // Assert the correct violation message
        for (ConstraintViolation<BorrowedBook> violation : violations) {
            if ("borrowDate".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Borrow date is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateReturnDateNotNull() {
        // Invalid BorrowedBook (Return date is null)
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), null, null, BorrowStatus.BORROWED);

        Set<ConstraintViolation<BorrowedBook>> violations = validator.validate(borrowedBook);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null return date.");

        // Assert the correct violation message
        for (ConstraintViolation<BorrowedBook> violation : violations) {
            if ("returnDate".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Return date is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateReturnDateFutureOrPresent() {
        // Invalid BorrowedBook (Return date is in the past)
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().minusDays(1), null, BorrowStatus.BORROWED);

        Set<ConstraintViolation<BorrowedBook>> violations = validator.validate(borrowedBook);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to return date being in the past.");

        // Assert the correct violation message
        for (ConstraintViolation<BorrowedBook> violation : violations) {
            if ("returnDate".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Return date must be today or in the future", violation.getMessage());
            }
        }
    }

    @Test
    public void validateStatusNotNull() {
        // Invalid BorrowedBook (Status is null)
        BorrowedBook borrowedBook = new BorrowedBook(LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, null);

        Set<ConstraintViolation<BorrowedBook>> violations = validator.validate(borrowedBook);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null status.");

        // Assert the correct violation message
        for (ConstraintViolation<BorrowedBook> violation : violations) {
            if ("status".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Borrow status is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidBorrowedBook() {
        // Valid BorrowedBook
        BorrowedBook borrowedBook = new BorrowedBook(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                null,
                BorrowStatus.BORROWED
        );

        Set<ConstraintViolation<BorrowedBook>> violations = validator.validate(borrowedBook);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }

        // There should be no violations for a valid borrowedBook
        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for a valid borrowedBook.");
    }

    @Test
    public void validateReturnedOnOptional() {
        // Valid BorrowedBook (ReturnedOn is null, which is allowed)
        BorrowedBook borrowedBook = new BorrowedBook(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                null,
                BorrowStatus.RETURNED
        );

        Set<ConstraintViolation<BorrowedBook>> violations = validator.validate(borrowedBook);

        Assertions.assertTrue(violations.isEmpty(), "Validation should pass when returnedOn is null.");
    }

}
