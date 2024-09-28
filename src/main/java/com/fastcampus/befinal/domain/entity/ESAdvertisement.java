package com.fastcampus.befinal.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setting(replicas = 0)
@Document(indexName = "Advertisement")
public class ESAdvertisement {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String advertisementId;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String product;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String advertiser;

    @Field(type = FieldType.Boolean)
    private Boolean state;

    @Field(type = FieldType.Boolean, docValues = false)
    private Boolean same;

    @Field(type = FieldType.Boolean, docValues = false)
    private Boolean issue;

//    @Field(type = FieldType.Date, format = DateFormat.strict_date_time_no_millis, index = false, docValues = false)
//    private LocalDateTime postDateTime;

    @Field(type = FieldType.Date, format = DateFormat.strict_date_time_no_millis, index = false, docValues = false)
    private LocalDateTime assignDateTime;
//
//    @Field(type = FieldType.Date, format = DateFormat.strict_date_time_no_millis, index = false, docValues = false)
//    private LocalDateTime taskDateTime;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String assignee;

//    @Field(type = FieldType.Keyword, index = false, docValues = false)
//    private String modifier;

    @Field(type = FieldType.Keyword, docValues = false)
    private String category;

    @Field(type = FieldType.Keyword, docValues = false)
    private String media;
}
