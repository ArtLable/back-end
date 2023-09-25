package com.artlable.backend.novel.command.application.dto.novelcharacter;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCreateCharacter {

    private String characterName;
    private String characterGender;
    private String characterAppearance;
    private String characterPersonality;

    @Builder
    public NovelCreateCharacter(String characterName, String characterGender, String characterAppearance, String characterPersonality) {
        this.characterName = characterName;
        this.characterGender = characterGender;
        this.characterAppearance = characterAppearance;
        this.characterPersonality = characterPersonality;
    }

    public NovelCharacter toEntity() {
        return NovelCharacter.builder()
                .characterName(characterName)
                .characterGender(characterGender)
                .characterAppearance(characterAppearance)
                .characterPersonality(characterPersonality)
                .build();
    }
}
