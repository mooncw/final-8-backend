package com.fastcampus.befinal.domain.command;

import lombok.Builder;

import java.util.List;

public class SameAdCommand {
    @Builder
    public record SameAdFilterConditionRequest(
        String cursorId,
        String keyword,
        String period,
        Boolean same,
        List<String> media,
        List<String> category
    ) {}
}
