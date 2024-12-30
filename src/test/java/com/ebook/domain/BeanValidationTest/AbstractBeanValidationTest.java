package com.ebook.domain.BeanValidationTest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;

public class AbstractBeanValidationTest {

    protected static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        // Common setup logic for all tests
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
