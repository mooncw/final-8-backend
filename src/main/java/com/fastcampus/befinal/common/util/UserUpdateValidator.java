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
        boolean emailIsNullOrEmpty = request.email() == null || request.email().isEmpty();

        boolean phoneNumberIsNullOrEmpty = request.phoneNumber() == null || request.phoneNumber().isEmpty();

        boolean certNoCheckTokenIsNullOrEmpty = request.certNoCheckToken() == null || request.certNoCheckToken().isEmpty();

        return !(emailIsNullOrEmpty && (phoneNumberIsNullOrEmpty || certNoCheckTokenIsNullOrEmpty));
    }
}
