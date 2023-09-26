package com.artlable.backend.novel.command.application.dto.novelsummary;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelUpdateSummaryDTO {

    private String summaryContent;
    private List<FileRequestDTO> filesList;

    public NovelUpdateSummaryDTO(String summaryContent, List<FileRequestDTO> filesList) {
        this.summaryContent = summaryContent;
        this.filesList = filesList;
    }
}
