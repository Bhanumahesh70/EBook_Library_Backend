package com.ebook.domain.BeanValidationTest;
import com.ebook.domain.Book;
import com.ebook.domain.Fine;
import com.ebook.domain.FinePaidStatus;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
public class FineValidationTest extends AbstractBeanValidationTest{
    @Test
    public void validateAmountNotNull() {
        // Invalid Fine (Amount is null)
        Fine fine = new Fine();
        fine.setStatus(FinePaidStatus.UNPAID);
        fine.setPaidDate(LocalDateTime.now());

        Set<ConstraintViolation<Fine>> violations = validator.validate(fine);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null amount.");

        for (ConstraintViolation<Fine> violation : violations) {
            if ("amount".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals( "Amount must be positive", violation.getMessage());
            }
        }
    }

    @Test
    public void validateAmountPositive() {
        // Invalid Fine (Amount is negative)
        Fine fine = new Fine(-50, FinePaidStatus.UNPAID, LocalDateTime.now());

        Set<ConstraintViolation<Fine>> violations = validator.validate(fine);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to negative amount.");

        for (ConstraintViolation<Fine> violation : violations) {
            if ("amount".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Amount must be positive", violation.getMessage());
            }
        }
    }

    @Test
    public void validateStatusNotNull() {
        // Invalid Fine (Status is null)
        Fine fine = new Fine(100, null, LocalDateTime.now());

        Set<ConstraintViolation<Fine>> violations = validator.validate(fine);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null status.");

        for (ConstraintViolation<Fine> violation : violations) {
            if ("status".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Fine status is mandatory", violation.getMessage());
            }
        }
    }

    @Test
    public void validatePaidDatePastOrPresent() {
        // Invalid Fine (Paid date is in the future)
        Fine fine = new Fine(100, FinePaidStatus.PAID, LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Fine>> violations = validator.validate(fine);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to future paid date.");

        for (ConstraintViolation<Fine> violation : violations) {
            if ("paidDate".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Paid date must be in the past or present", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidFine() {
        // Valid Fine
        Fine fine = new Fine(100, FinePaidStatus.PAID, LocalDateTime.now());

        Set<ConstraintViolation<Fine>> violations = validator.validate(fine);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }

        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for a valid Fine object.");
    }

    @Test
    public void validatePaidDateOptional() {
        // Valid Fine (Paid date is null, which is allowed)
        Fine fine = new Fine(100, FinePaidStatus.UNPAID, null);

        Set<ConstraintViolation<Fine>> violations = validator.validate(fine);

        Assertions.assertTrue(violations.isEmpty(), "Validation should pass when paid date is null.");
    }
}
