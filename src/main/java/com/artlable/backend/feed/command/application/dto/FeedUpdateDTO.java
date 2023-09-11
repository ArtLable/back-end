package com.artlable.backend.feed.command.application.dto;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FeedUpdateDTO {

    private Long feedNo;
    private Member memberNo;
    private String feedContent;
    private String feedCategory;
    private boolean feedIsDeleted;
    private LocalDateTime createdDate;

    public FeedUpdateDTO(Long feedNo, Member memberNo, String feedContent, String feedCategory, boolean feedIsDeleted, LocalDateTime createdDate) {
        this.feedNo = feedNo;
        this.memberNo = memberNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.feedIsDeleted = feedIsDeleted;
        this.createdDate = createdDate;
    }

    public void setFeedNo(Long feedNo) {
        this.feedNo = feedNo;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    public void setFeedIsDeleted(boolean feedIsDeleted) {
        this.feedIsDeleted = feedIsDeleted;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
