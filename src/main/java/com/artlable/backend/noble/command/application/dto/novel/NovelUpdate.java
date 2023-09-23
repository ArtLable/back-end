package com.artlable.backend.noble.command.application.dto.novel;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelUpdate {

    private String novelContent;
    private String novelTitle;
    private String novelGenre;
    private List<FileRequestDTO> filesList;

    public NovelUpdate(String novelContent, String novelTitle, String novelGenre, List<FileRequestDTO> filesList) {
        this.novelContent = novelContent;
        this.novelTitle = novelTitle;
        this.novelGenre = novelGenre;
        this.filesList = filesList;
    }
}
