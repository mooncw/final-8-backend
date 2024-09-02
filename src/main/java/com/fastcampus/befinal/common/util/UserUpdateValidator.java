package com.fastcampus.befinal.common.util;

import com.fastcampus.befinal.common.annotation.ValidUserUpdate;
import com.fastcampus.befinal.presentation.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserUpdateValidator implements ConstraintValidator<ValidUserUpdate, UserDto.UserUpdateRequest> {

    @Override
    public void initialize(ValidUserUpdate constraintAnnotation){

    }

    @Override
    public boolean isValid(UserDto.UserUpdateRequest request, ConstraintValidatorContext context){
        boolean isEmailOrPhoneNumberProvided =
            request.email() != null || request.phoneNumber() != null;

        boolean isPhoneNumberValid =
            request.phoneNumber() == null || request.certNoCheckToken() != null;

        return isEmailOrPhoneNumberProvided && isPhoneNumberValid;
    }
}
