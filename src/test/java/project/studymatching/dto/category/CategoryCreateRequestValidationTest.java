package project.studymatching.dto.category;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static project.studymatching.factory.dto.CategoryCreateRequestFactory.createCategoryCreateRequest;
import static project.studymatching.factory.dto.CategoryCreateRequestFactory.createCategoryCreateRequestWithName;

class CategoryCreateRequestValidationTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateTest() {
        // given
        CategoryCreateRequest req = createCategoryCreateRequest();

        // when
        Set<ConstraintViolation<CategoryCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isEmpty();
    }

    @Test
    void invalidateByEmptyNameTest() {
        // given
        String invalidValue = null;
        CategoryCreateRequest req = createCategoryCreateRequestWithName(invalidValue);

        // when
        Set<ConstraintViolation<CategoryCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByBlankNameTest() {
        // given
        String invalidValue = " ";
        CategoryCreateRequest req = createCategoryCreateRequestWithName(invalidValue);

        // when
        Set<ConstraintViolation<CategoryCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByShortNameTest() {
        // given
        String invalidValue = "c";
        CategoryCreateRequest req = createCategoryCreateRequestWithName(invalidValue);

        // when
        Set<ConstraintViolation<CategoryCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByLongNameTest() {
        // given
        String invalidValue = "c".repeat(50);

        CategoryCreateRequest req = createCategoryCreateRequestWithName(invalidValue);

        // when
        Set<ConstraintViolation<CategoryCreateRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }
}