package com.artlable.backend.novel.command.application.dto.novel;

import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelRead {

    private Long novelNo;
    private String novelTitle;
    private String novelContent;
    private String novelGenre;
    private List<Long> fileNo;

    public NovelRead(Novel novel) {
        this.novelNo = novel.getNovelNo();
        this.novelTitle = novel.getNovelTitle();
        this.novelContent = novel.getNovelContent();
        this.novelGenre = novel.getNovelGenre();
    }
    @Builder
    public NovelRead(Long novelNo, String novelTitle, String novelContent, String novelGenre, List<Long> fileNo) {
        this.novelNo = novelNo;
        this.novelTitle = novelTitle;
        this.novelContent = novelContent;
        this.novelGenre = novelGenre;
        this.fileNo = fileNo;
    }
}
