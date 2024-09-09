package com.fastcampus.befinal.common.util;

import com.fastcampus.befinal.common.annotation.ValidPeriod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PeriodValidator implements ConstraintValidator<ValidPeriod, String> {
    private static final Pattern PERIOD_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-[12]$");

    @Override
    public boolean isValid(String period, ConstraintValidatorContext constraintValidatorContext) {
        if(period == null) {
            return true;
        }
        return PERIOD_PATTERN.matcher(period).matches();
    }
}
