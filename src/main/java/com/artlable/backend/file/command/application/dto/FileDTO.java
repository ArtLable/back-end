package com.artlable.backend.file.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDTO {

    private String webToonFile;
    private String nobleFile;

    public FileDTO(String webToonFile, String nobleFile) {
        this.webToonFile = webToonFile;
        this.nobleFile = nobleFile;
    }

    public void setWebToonFile(String webToonFile) {
        this.webToonFile = webToonFile;
    }

    public void setNobleFile(String nobleFile) {
        this.nobleFile = nobleFile;
    }
}
