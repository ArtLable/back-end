package com.artlable.backend.novel.command.application.dto.novelsummary;

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
    private String summaryResult;
    private List<Long> fileNo;

    public NovelReadSummary(NovelSummary novelSummary) {
        this.summaryNo = novelSummary.getSummaryNo();
        this.summaryContent = novelSummary.getSummaryContent();
        this.summaryResult = novelSummary.getSummaryResult();
        this.fileNo = novelSummary.getFiles().stream().map(Files::getFileNo)
                .collect(Collectors.toList());
    }

    @Builder
    public NovelReadSummary(Long summaryNo, String summaryContent, String summaryResult, List<Long> fileNo) {
        this.summaryNo = summaryNo;
        this.summaryContent = summaryContent;
        this.summaryResult = summaryResult;
        this.fileNo = fileNo;
    }
}
