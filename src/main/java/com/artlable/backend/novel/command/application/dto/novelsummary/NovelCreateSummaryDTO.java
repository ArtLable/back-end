package com.artlable.backend.novel.command.application.dto.novelsummary;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;

import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCreateSummaryDTO {

    private String summaryContent;
//    private List<Files> files;

    @Builder
    public NovelCreateSummaryDTO(String summaryContent, List<Files> files) {
        this.summaryContent = summaryContent;
//        this.files = files;
    }

    public NovelSummary toEntity() {
        return NovelSummary.builder()
                .summaryContent(summaryContent)
                .build();
    }
}
