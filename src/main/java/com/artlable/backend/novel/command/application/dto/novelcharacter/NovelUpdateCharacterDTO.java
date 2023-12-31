package com.artlable.backend.novel.command.application.dto.novelcharacter;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelUpdateCharacterDTO {

    private String characterName;
    private String characterGender;
    private String characterAppearance;
    private String characterPersonality;
    private List<FileRequestDTO> filesList;

    public NovelUpdateCharacterDTO(String characterName, String characterGender, String characterAppearance, String characterPersonality, List<FileRequestDTO> filesList) {
        this.characterName = characterName;
        this.characterGender = characterGender;
        this.characterAppearance = characterAppearance;
        this.characterPersonality = characterPersonality;
        this.filesList = filesList;
    }
}
