package com.fastcampus.befinal.domain.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RefreshToken.class, name = "RefreshToken"),
    @JsonSubTypes.Type(value = SmsCertification.class, name = "SmsCertification")
})
public interface RedisValue {
}
