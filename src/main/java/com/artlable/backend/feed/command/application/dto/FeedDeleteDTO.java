package com.artlable.backend.feed.command.application.dto;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FeedDeleteDTO {

    private Long feedNo;
    private Member memberNo;
    private String feedContent;
    private boolean feedIsDeleted;

    public FeedDeleteDTO(Long feedNo, Member memberNo, String feedContent, boolean feedIsDeleted) {
        this.feedNo = feedNo;
        this.memberNo = memberNo;
        this.feedContent = feedContent;
        this.feedIsDeleted = feedIsDeleted;
    }
}
