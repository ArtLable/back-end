package com.artlable.backend.novel.command.application.dto.novelsummary;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;

import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCreateSummaryDTO {

    private String summaryContent;
    private List<MultipartFile> files;

    @Builder
    public NovelCreateSummaryDTO(String summaryContent, List<MultipartFile> files) {
        this.summaryContent = summaryContent;
        this.files = files;
    }

    public NovelSummary toEntity() {
        return NovelSummary.builder()
                .summaryContent(summaryContent)
                .build();
    }
}
