package com.artlable.backend.novel.command.application.dto.novelcharacter;

import com.artlable.backend.files.command.application.dto.ReadFeedFileResponseDTO;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelReadCharacterDTO {

    private Long characterNo;
    private String characterName;
    private String characterGender;
    private String characterAppearance;
    private String characterPersonality;
    private List<ReadFeedFileResponseDTO> files;

    public NovelReadCharacterDTO(NovelCharacter novelCharacter) {
        this.characterNo = novelCharacter.getCharacterNo();
        this.characterName = novelCharacter.getCharacterName();
        this.characterGender = novelCharacter.getCharacterGender();
        this.characterAppearance = novelCharacter.getCharacterAppearance();
        this.characterPersonality = novelCharacter.getCharacterPersonality();
        this.files = novelCharacter.getFiles().stream()
                .map(ReadFeedFileResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
    @Builder
    public NovelReadCharacterDTO(Long characterNo, String characterName, String characterGender, String characterAppearance,
                                 String characterPersonality, List<ReadFeedFileResponseDTO> files) {
        this.characterNo = characterNo;
        this.characterName = characterName;
        this.characterGender = characterGender;
        this.characterAppearance = characterAppearance;
        this.characterPersonality = characterPersonality;
        this.files = files;
    }

    public static NovelReadCharacterDTO fromEntity(NovelCharacter novelCharacter) {
        return NovelReadCharacterDTO.builder()
                .characterNo(novelCharacter.getCharacterNo())
                .characterName(novelCharacter.getCharacterName())
                .characterGender(novelCharacter.getCharacterGender())
                .characterAppearance(novelCharacter.getCharacterAppearance())
                .characterPersonality(novelCharacter.getCharacterPersonality())
                .build();
    }
}
