package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningUpdate {

    private String learningContent;
    private List<FileRequestDTO> filesList;

    public LearningUpdate(String learningContent, List<FileRequestDTO> filesList) {
        this.learningContent = learningContent;
        this.filesList = filesList;
    }
}
