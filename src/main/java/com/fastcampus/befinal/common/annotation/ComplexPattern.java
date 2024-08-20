package com.fastcampus.befinal.common.annotation;

import com.fastcampus.befinal.common.util.ComplexPatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ComplexPatternValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexPattern {
    String message() default "ComplexPattern validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] patterns();
    int minMatches() default 1;
}
