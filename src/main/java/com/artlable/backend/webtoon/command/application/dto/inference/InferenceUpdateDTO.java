package com.artlable.backend.webtoon.command.application.dto.inference;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceUpdateDTO {

    private String inferenceContent;
    private String inferenceResult;
    private List<FileRequestDTO> filesList;

    public InferenceUpdateDTO(String inferenceContent, String inferenceResult, List<FileRequestDTO> filesList) {
        this.inferenceContent = inferenceContent;
        this.inferenceResult = inferenceResult;
        this.filesList = filesList;
    }
}
