package com.artlable.backend.novel.command.application.dto.novel;

import com.artlable.backend.files.command.application.dto.novel.ReadNovelFileRequestDTO;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelReadCharacterDTO;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelReadSummaryDTO;
import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelReadDTO {

    private Long novelNo;
    private String novelTitle;
    private String novelContent;
    private String novelGenre;
    private List<NovelReadCharacterDTO> characters;
    private List<NovelReadSummaryDTO> summaries;
    private List<ReadNovelFileRequestDTO> files;

    @Builder
    public NovelReadDTO(Long novelNo, String novelTitle, String novelContent, String novelGenre, List<NovelReadCharacterDTO> characters, List<NovelReadSummaryDTO> summaries, List<ReadNovelFileRequestDTO> files) {
        this.novelNo = novelNo;
        this.novelTitle = novelTitle;
        this.novelContent = novelContent;
        this.novelGenre = novelGenre;
        this.characters = characters;
        this.summaries = summaries;
        this.files = files;
    }

    public NovelReadDTO(Novel novel) {
        this.novelNo = novel.getNovelNo();
        this.novelTitle = novel.getNovelTitle();
        this.novelContent = novel.getNovelContent();
        this.novelGenre = novel.getNovelGenre();
        this.characters = novel.getCharacters().stream()
                .map(NovelReadCharacterDTO::fromEntity)
                .collect(Collectors.toList());
        this.summaries = novel.getSummaries().stream()
                .map(NovelReadSummaryDTO::fromEntity)
                .collect(Collectors.toList());
        this.files = novel.getFiles().stream()
                .map(ReadNovelFileRequestDTO::fromEntity)
                .collect(Collectors.toList());
    }


}
