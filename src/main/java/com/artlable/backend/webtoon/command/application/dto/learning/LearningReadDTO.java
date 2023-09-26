package com.artlable.backend.webtoon.command.application.dto.learning;

import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningReadDTO {

    private Long learningNo;
    private String cname;
    private String searchText;


    public LearningReadDTO(Learning learning) {
        this.learningNo = learning.getLearningNo();
        this.cname = learning.getCname();
        this.searchText = learning.getSearchText();

    }
    @Builder
    public LearningReadDTO(Long learningNo, String cname, String searchText) {
        this.learningNo = learningNo;
        this.cname = cname;
        this.searchText = searchText;
    }
}
