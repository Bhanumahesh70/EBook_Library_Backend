package com.ebook.domain.BeanValidationTest;
import com.ebook.domain.Publisher;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;
public class PublisherValidationTest extends AbstractBeanValidationTest{
    @Test
    public void validateNameNotNullAndSize() {
        // Invalid Publisher (Name is null)
        Publisher publisher = new Publisher(null, "123 Address", "test@example.com", "+1234567890");

        Set<ConstraintViolation<Publisher>> violations = validator.validate(publisher);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null name.");

        for (ConstraintViolation<Publisher> violation : violations) {
            if ("name".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Name is mandatory", violation.getMessage());
            }
        }

        // Invalid Publisher (Name exceeds max length)
        publisher.setName("A".repeat(101));

        violations = validator.validate(publisher);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to name exceeding max length.");

        for (ConstraintViolation<Publisher> violation : violations) {
            if ("name".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Name must not exceed 100 characters", violation.getMessage());
            }
        }
    }

    @Test
    public void validateEmailNotNullAndValid() {
        // Invalid Publisher (Email is null)
        Publisher publisher = new Publisher("PublisherName", "123 Address", null, "+1234567890");

        Set<ConstraintViolation<Publisher>> violations = validator.validate(publisher);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to null email.");

        for (ConstraintViolation<Publisher> violation : violations) {
            if ("email".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Email is mandatory", violation.getMessage());
            }
        }

        // Invalid Publisher (Email is invalid)
        publisher.setEmail("invalid-email");

        violations = validator.validate(publisher);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to invalid email format.");

        for (ConstraintViolation<Publisher> violation : violations) {
            if ("email".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Please provide a valid email address", violation.getMessage());
            }
        }
    }

    @Test
    public void validatePhoneNumberPattern() {
        // Invalid Publisher (Phone number does not match pattern)
        Publisher publisher = new Publisher("PublisherName", "123 Address", "test@example.com", "12345");

        Set<ConstraintViolation<Publisher>> violations = validator.validate(publisher);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to invalid phone number format.");

        for (ConstraintViolation<Publisher> violation : violations) {
            if ("phoneNumber".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Phone number must be between 10 and 15 digits and can optionally start with a '+'", violation.getMessage());
            }
        }
    }

    @Test
    public void validateAddressSize() {
        // Invalid Publisher (Address exceeds max length)
        Publisher publisher = new Publisher("PublisherName", "A".repeat(256), "test@example.com", "+1234567890");

        Set<ConstraintViolation<Publisher>> violations = validator.validate(publisher);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to address exceeding max length.");

        for (ConstraintViolation<Publisher> violation : violations) {
            if ("address".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Address must not exceed 255 characters", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidPublisher() {
        // Valid Publisher
        Publisher publisher = new Publisher("PublisherName", "123 Address", "test@example.com", "+1234567890");

        Set<ConstraintViolation<Publisher>> violations = validator.validate(publisher);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }

        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for a valid Publisher object.");
    }
}

