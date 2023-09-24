package com.artlable.backend.webtoon.command.application.dto.inference;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceUpdate {

    private String inferenceContent;
    private String inferenceResult;
    private List<FileRequestDTO> filesList;

    public InferenceUpdate(String inferenceContent, String inferenceResult, List<FileRequestDTO> filesList) {
        this.inferenceContent = inferenceContent;
        this.inferenceResult = inferenceResult;
        this.filesList = filesList;
    }
}
