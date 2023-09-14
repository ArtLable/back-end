package com.artlable.backend.feed.command.application.dto;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class FeedDeleteDTO {

    private Long feedNo;
    private String feedContent;


    public FeedDeleteDTO(Long feedNo, String feedContent) {
        this.feedNo = feedNo;
        this.feedContent = feedContent;
    }

    public void setFeedNo(Long feedNo) {
        this.feedNo = feedNo;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }
}
