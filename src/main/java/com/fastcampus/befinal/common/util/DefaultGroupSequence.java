package com.fastcampus.befinal.common.util;

import jakarta.validation.GroupSequence;

@GroupSequence({RequestValidationGroups.NotNullGroup.class, RequestValidationGroups.NotEmptyGroup.class,
    RequestValidationGroups.NotBlankGroup.class, RequestValidationGroups.SizeGroup.class,
    RequestValidationGroups.PatternGroup.class, RequestValidationGroups.CustomValidateGroup.class})
public interface DefaultGroupSequence {
}
