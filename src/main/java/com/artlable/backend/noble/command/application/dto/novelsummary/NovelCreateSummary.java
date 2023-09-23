package com.artlable.backend.noble.command.application.dto.novelsummary;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.noble.command.domain.aggregate.entity.NovelSummary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCreateSummary {

    private String summaryContent;
    private String summaryResult;
    private List<Files> files;

    @Builder
    public NovelCreateSummary(String summaryContent, String summaryResult, List<Files> files) {
        this.summaryContent = summaryContent;
        this.summaryResult = summaryResult;
        this.files = files;
    }

    public NovelSummary toEntity() {
        return NovelSummary.builder()
                .summaryContent(summaryContent)
                .summaryResult(summaryResult)
                .build();
    }
}
