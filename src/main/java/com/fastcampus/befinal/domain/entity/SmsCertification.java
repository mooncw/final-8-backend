package com.fastcampus.befinal.domain.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName(value = "SmsCertification")
public class SmsCertification implements SmsValue {
    private String certificationNumber;
}
