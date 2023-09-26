package com.artlable.backend.novel.command.application.dto.novelcharacter;

import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCreateCharacterDTO {

    private String characterTitle;
    private String characterName;
    private String characterGender;
    private String characterAppearance;
    private String characterPersonality;
    private String characterGenre;

    @Builder
    public NovelCreateCharacterDTO(String characterTitle, String characterName, String characterGender,
                                   String characterAppearance, String characterPersonality, String characterGenre) {
        this.characterTitle = characterTitle;
        this.characterName = characterName;
        this.characterGender = characterGender;
        this.characterAppearance = characterAppearance;
        this.characterPersonality = characterPersonality;
        this.characterGenre = characterGenre;
    }

    public NovelCharacter toEntity() {
        return NovelCharacter.builder()
                .characterTitle(characterTitle)
                .characterName(characterName)
                .characterGender(characterGender)
                .characterAppearance(characterAppearance)
                .characterPersonality(characterPersonality)
                .characterGenre(characterGenre)
                .build();
    }
}
