package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningCreate {

    private String learningContent;
    private List<Files> files;

    @Builder
    public LearningCreate(String learningContent, List<Files> files) {
        this.learningContent = learningContent;
        this.files = files;
    }

    public Learning toEntity() {
        return Learning.builder()
                .learningContent(learningContent)
                .files(files)
                .build();
    }
}
