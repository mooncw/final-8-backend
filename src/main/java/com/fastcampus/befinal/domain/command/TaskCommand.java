package com.fastcampus.befinal.domain.command;

import lombok.Builder;
import java.util.List;

public class TaskCommand {
    @Builder
    public record FilterConditionRequest(
        CursorInfo cursorInfo,
        String keyword,
        String period,
        Boolean state,
        Boolean issue,
        List<String> media,
        List<String> category
    ) {}

    @Builder
    public record CursorInfo(
            Boolean cursorState,
            String cursorId
    ) {}
}
