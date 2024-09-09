package com.fastcampus.befinal.common.annotation;

import com.fastcampus.befinal.common.util.PeriodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.fastcampus.befinal.common.contant.TaskConstant.PATTERN_MISMATCH_PERIOD;

@Constraint(validatedBy = PeriodValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriod {
    String message() default PATTERN_MISMATCH_PERIOD;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
