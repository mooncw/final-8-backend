package com.fastcampus.befinal.common.util;

import com.fastcampus.befinal.common.annotation.ComplexPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ComplexPatternValidator implements ConstraintValidator<ComplexPattern, String> {
    private String[] patterns;
    private int minMatches;

    @Override
    public void initialize(ComplexPattern constraintAnnotation) {
        this.patterns = constraintAnnotation.patterns();
        this.minMatches = constraintAnnotation.minMatches();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        int matchCount = 0;

        for (String regex : patterns) {
            if (Pattern.compile(regex).matcher(value).find()) {
                matchCount++;
            }

            if (matchCount >= minMatches) {
                return true;
            }
        }

        return false;
    }
}
