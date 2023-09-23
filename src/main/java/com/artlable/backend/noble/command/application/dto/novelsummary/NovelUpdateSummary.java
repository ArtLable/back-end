package com.artlable.backend.noble.command.application.dto.novelsummary;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelUpdateSummary {

    private String summaryContent;
    private String summaryResult;
    private List<FileRequestDTO> filesList;

    public NovelUpdateSummary(String summaryContent, String summaryResult, List<FileRequestDTO> filesList) {
        this.summaryContent = summaryContent;
        this.summaryResult = summaryResult;
        this.filesList = filesList;
    }
}
