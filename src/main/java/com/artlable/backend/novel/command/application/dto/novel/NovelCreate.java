package com.artlable.backend.novel.command.application.dto.novel;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCreate {

    private String novelTitle;
    private String novelContent;
    private String novelGenre;

    @Builder
    public NovelCreate(String novelTitle, String novelContent, String novelGenre) {
        this.novelTitle = novelTitle;
        this.novelContent = novelContent;
        this.novelGenre = novelGenre;
    }

    public Novel toEntity(){
        return Novel.builder()
                .novelTitle(novelTitle)
                .novelContent(novelContent)
                .novelGenre(novelGenre)
                .build();
    }
}
