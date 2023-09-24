package com.artlable.backend.novel.command.application.dto.novelcharacter;

import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelReadCharacter {

    private Long characterNo;
    private String characterName;
    private String characterGender;
    private String characterAppearance;
    private String characterPersonality;
    private String characterResult;
    private List<Long> fileNo;

    public NovelReadCharacter(NovelCharacter novelCharacter) {
        this.characterNo = novelCharacter.getCharacterNo();
        this.characterName = novelCharacter.getCharacterName();
        this.characterGender = novelCharacter.getCharacterGender();
        this.characterAppearance = novelCharacter.getCharacterAppearance();
        this.characterPersonality = novelCharacter.getCharacterPersonality();
        this.characterResult = novelCharacter.getCharacterResult();
    }
    @Builder
    public NovelReadCharacter(Long characterNo, String characterName, String characterGender, String characterAppearance, String characterPersonality, String characterResult, List<Long> fileNo) {
        this.characterNo = characterNo;
        this.characterName = characterName;
        this.characterGender = characterGender;
        this.characterAppearance = characterAppearance;
        this.characterPersonality = characterPersonality;
        this.characterResult = characterResult;
        this.fileNo = fileNo;
    }
}
