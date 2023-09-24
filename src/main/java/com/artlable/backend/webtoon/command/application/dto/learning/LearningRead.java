package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningRead {

    private Long learningNo;
    private String learningContent;
    private List<Long> fileNo;

    public LearningRead(Learning learning) {
        this.learningNo = learning.getLearningNo();
        this.learningContent = learning.getLearningContent();
    }
    @Builder
    public LearningRead(Long learningNo, String learningContent, List<Long> fileNo) {
        this.learningNo = learningNo;
        this.learningContent = learningContent;
        this.fileNo = fileNo;
    }
}
