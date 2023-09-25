package com.artlable.backend.novel.command.application.dto.novel;

import com.artlable.backend.files.command.application.dto.ReadFeedFileResponseDTO;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelReadCharacterDTO;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelReadSummary;
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
//    private List<NovelReadResult> resultList;
    private List<NovelReadCharacterDTO> characters;
    private List<NovelReadSummary> summaries;
    private List<ReadFeedFileResponseDTO> files;

    public NovelReadDTO(Novel novel) {
        this.novelNo = novel.getNovelNo();
        this.novelTitle = novel.getNovelTitle();
        this.novelContent = novel.getNovelContent();
        this.novelGenre = novel.getNovelGenre();
//        this.resultList = novel.getResultList().stream()
//                .map(NovelReadResult::fromEntity)
//                .collect(Collectors.toList());
        this.characters = novel.getCharacters().stream()
                .map(NovelReadCharacterDTO::fromEntity)
                .collect(Collectors.toList());
        this.summaries = novel.getSummaries().stream()
                .map(NovelReadSummary::fromEntity)
                .collect(Collectors.toList());
        this.files = novel.getFiles().stream()
                .map(ReadFeedFileResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
    @Builder
    public NovelReadDTO(Long novelNo, String novelTitle, String novelContent, String novelGenre, List<NovelReadCharacterDTO> characters, List<NovelReadSummary> summaries, List<ReadFeedFileResponseDTO> files) {
        this.novelNo = novelNo;
        this.novelTitle = novelTitle;
        this.novelContent = novelContent;
        this.novelGenre = novelGenre;
//        this.resultList = resultList;
        this.characters = characters;
        this.summaries = summaries;
        this.files = files;
    }

//    public static NovelRead fromEntity(Novel novel) {
//        return NovelRead.builder()
//                .novelNo(novel.getNovelNo())
//                .novelTitle(novel.getNovelTitle())
//                .novelContent(novel.getNovelContent())
//                .novelGenre(novel.getNovelGenre())
////                .resultList(novel.getResultList())
//                .files(novel.getFiles())
//                .build();
//    }
}
