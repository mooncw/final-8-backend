package com.fastcampus.befinal.domain.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "SmsCertification")
public class SmsCertification implements SmsValue {
    private String certificationNumber;
}
