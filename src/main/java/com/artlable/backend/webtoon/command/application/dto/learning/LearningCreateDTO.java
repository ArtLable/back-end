package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.files.command.application.dto.webtoon.CreateWebtoonLerningFileRequestDTO;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningCreateDTO {

    private String cname;
    private String searchText;

    @Builder
    public LearningCreateDTO(String cname, String searchText) {
        this.cname = cname;
        this.searchText = searchText;
    }

    public Learning toEntity() {
        return Learning.builder()
                .canme(cname)
                .searchText(searchText)
                .build();
    }
}