package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningUpdateDTO {

    private String cname;
    private String searchText;
    private List<FileRequestDTO> filesList;

    public LearningUpdateDTO(String cname, String searchText, List<FileRequestDTO> filesList) {
        this.cname = cname;
        this.searchText = searchText;
        this.filesList = filesList;
    }
}
