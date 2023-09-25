package com.artlable.backend.novel.command.application.dto.novelsummary;

import com.artlable.backend.files.command.application.dto.feed.ReadFeedFileResponseDTO;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelReadSummaryDTO {

    private Long summaryNo;
    private String summaryContent;
    private List<ReadFeedFileResponseDTO> files;

    public NovelReadSummaryDTO(NovelSummary novelSummary) {
        this.summaryNo = novelSummary.getSummaryNo();
        this.summaryContent = novelSummary.getSummaryContent();
        this.files = novelSummary.getFiles().stream()
                .map(ReadFeedFileResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Builder
    public NovelReadSummaryDTO(Long summaryNo, String summaryContent, List<ReadFeedFileResponseDTO> files) {
        this.summaryNo = summaryNo;
        this.summaryContent = summaryContent;
        this.files = files;
    }

    public static NovelReadSummaryDTO fromEntity(NovelSummary novelSummary) {
        return NovelReadSummaryDTO.builder()
                .summaryNo(novelSummary.getSummaryNo())
                .summaryContent(novelSummary.getSummaryContent())
                .build();
    }
}
