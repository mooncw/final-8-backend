package com.fastcampus.befinal.domain.command;

import lombok.Builder;

public class FilterCommand {
    @Builder
    public record ConditionCommand(
        String keyword,
        String period
    ) {}
}
