package com.ebook.domain.BeanValidationTest;

import com.ebook.domain.User;
import com.ebook.domain.UserRole;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Set;


public class UserBeanValidationTest extends AbstractBeanValidationTest {

    @Test
    public void validateNameNotBlank() {
        // Invalid User (Name is blank)
        User user = new User("", "bhanu@email.com", "ninini", "909090900", "kk", UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to blank name.");

        // Assert the correct violation message
        for (ConstraintViolation<User> violation : violations) {
            if ("name".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("may not be empty", violation.getMessage());
            }
        }
    }

    @Test
    public void validateEmail() {
        // Invalid User (Invalid Email)
        User user = new User("Bhanu", "invalid-email", "password123", "909090900", "kk", UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to invalid email.");

        // Assert the correct violation message
        for (ConstraintViolation<User> violation : violations) {
            if ("email".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Invalid Email address", violation.getMessage());
            }
        }
    }

    @Test
    public void validatePasswordMinLength() {
        // Invalid User (Password too short)
        User user = new User("Bhanu", "bhanu@email.com", "short", "909090900", "kk", UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertFalse(violations.isEmpty(), "Validation should fail due to short password.");

        // Assert the correct violation message
        for (ConstraintViolation<User> violation : violations) {
            if ("password".equals(violation.getPropertyPath().toString())) {
                Assertions.assertEquals("Password Should be minimum 8 characters", violation.getMessage());
            }
        }
    }

    @Test
    public void validateValidUser() {
        // Valid User
        User user = new User("Bhanu", "bhanu@email.com", "password123", "909090900", "kk", UserRole.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            violations.forEach(violation -> System.out.println(
                    "Violation: Property - " + violation.getPropertyPath() +
                            ", Message - " + violation.getMessage()));
        }
        // There should be no violations for a valid user
        Assertions.assertTrue(violations.isEmpty(), "Validation should pass for valid user.");
    }

}
