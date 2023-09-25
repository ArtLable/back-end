package com.artlable.backend.novel.command.application.dto.novelsummary;

import com.artlable.backend.files.command.application.dto.ReadFeedFileResponseDTO;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelReadSummary {

    private Long summaryNo;
    private String summaryContent;
    private List<ReadFeedFileResponseDTO> files;

    public NovelReadSummary(NovelSummary novelSummary) {
        this.summaryNo = novelSummary.getSummaryNo();
        this.summaryContent = novelSummary.getSummaryContent();
        this.files = novelSummary.getFiles().stream()
                .map(ReadFeedFileResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Builder
    public NovelReadSummary(Long summaryNo, String summaryContent, List<ReadFeedFileResponseDTO> files) {
        this.summaryNo = summaryNo;
        this.summaryContent = summaryContent;
        this.files = files;
    }

    public static NovelReadSummary fromEntity(NovelSummary novelSummary) {
        return NovelReadSummary.builder()
                .summaryNo(novelSummary.getSummaryNo())
                .summaryContent(novelSummary.getSummaryContent())
                .build();
    }
}
