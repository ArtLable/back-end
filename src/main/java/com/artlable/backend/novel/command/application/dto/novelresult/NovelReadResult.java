//package com.artlable.backend.novel.command.application.dto.novelresult;
//
//import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
//import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
//import com.artlable.backend.novel.command.domain.aggregate.entity.NovelResult;
//import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class NovelReadResult {
//
//    private Long resultNo;
//    private Novel novelNo;
//    private Novel novelTitle;
//    private Novel novelContent;
//    private Novel novelGenre;
//    private NovelCharacter characterNo;
//    private NovelCharacter characterName;
//    private NovelCharacter characterGender;
//    private NovelCharacter characterAppearance;
//    private NovelCharacter characterPersonality;
//    private NovelSummary summaryNo;
//    private NovelSummary summaryContent;
//
//    @Builder
//    public NovelReadResult(Long resultNo, Novel novelNo, Novel novelTitle, Novel novelContent, Novel novelGenre, NovelCharacter characterNo, NovelCharacter characterName, NovelCharacter characterGender, NovelCharacter characterAppearance, NovelCharacter characterPersonality, NovelSummary summaryNo, NovelSummary summaryContent) {
//        this.resultNo = resultNo;
//        this.novelNo = novelNo;
//        this.novelTitle = novelTitle;
//        this.novelContent = novelContent;
//        this.novelGenre = novelGenre;
//        this.characterNo = characterNo;
//        this.characterName = characterName;
//        this.characterGender = characterGender;
//        this.characterAppearance = characterAppearance;
//        this.characterPersonality = characterPersonality;
//        this.summaryNo = summaryNo;
//        this.summaryContent = summaryContent;
//    }
//
//    public static NovelReadResult fromEntity(NovelResult novelResult) {
//        return NovelReadResult.builder()
//                .resultNo(novelResult.getResultNo())
//                .novelNo(novelResult.getNovel())
//                .novelTitle(novelResult.getNovel())
//                .novelContent(novelResult.getNovel())
//                .novelGenre(novelResult.getNovel())
//                .characterNo((NovelCharacter) novelResult.getNovelCharacter())
//                .characterName((NovelCharacter) novelResult.getNovelCharacter())
//                .characterGender((NovelCharacter) novelResult.getNovelCharacter())
//                .characterAppearance((NovelCharacter) novelResult.getNovelCharacter())
//                .characterPersonality((NovelCharacter) novelResult.getNovelCharacter())
//                .summaryNo((NovelSummary) novelResult.getNovelSummary())
//                .summaryContent((NovelSummary) novelResult.getNovelSummary())
//                .build();
//    }
//}
