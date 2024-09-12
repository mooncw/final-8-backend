package com.fastcampus.befinal.common.util;

import com.fastcampus.befinal.common.annotation.ValidAdReviewType;
import com.fastcampus.befinal.presentation.dto.IssueAdDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class AdReviewTypeValidator implements ConstraintValidator<ValidAdReviewType, IssueAdDto.IssueAdReviewRequest> {

    @Override
    public void initialize(ValidAdReviewType constraintAnnotation){

    }

    @Override
    public boolean isValid(IssueAdDto.IssueAdReviewRequest request, ConstraintValidatorContext context){
        boolean reviewIdIsNull = request.reviewId() == null;
        boolean advertisementIdIsNullOrEmpty = request.advertisementId() == null || request.advertisementId().isEmpty();
        boolean provisionIdIsNull = request.provisionId() == null;
        boolean sentenceIsNullOrEmpty = request.sentence() == null || request.sentence().isEmpty();
        boolean opinionIsNullOrEmpty = request.opinion() == null || request.opinion().isEmpty();

        boolean validResult = true;

        if(Objects.equals(request.operationType(), "Create")){
            validResult = !(advertisementIdIsNullOrEmpty || provisionIdIsNull || sentenceIsNullOrEmpty || opinionIsNullOrEmpty);
        } else if (Objects.equals(request.operationType(),"Update")) {
            validResult = !(reviewIdIsNull || sentenceIsNullOrEmpty || opinionIsNullOrEmpty || provisionIdIsNull);
        } else if (Objects.equals(request.operationType(), "Delete")) {
            validResult = !(reviewIdIsNull);
        }

        return validResult;
    }
}
