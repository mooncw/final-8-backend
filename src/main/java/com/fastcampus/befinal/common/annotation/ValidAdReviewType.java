package com.fastcampus.befinal.common.annotation;

import com.fastcampus.befinal.common.util.AdReviewTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AdReviewTypeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAdReviewType {
    String message() default "Invalid AdReview Request.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
