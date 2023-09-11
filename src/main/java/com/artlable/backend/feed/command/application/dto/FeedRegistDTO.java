package com.artlable.backend.feed.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class FeedRegistDTO {

    private Long feedNo;
    private Long memberNo;
    private String feedContent;
    private String feedCategory;
    private Date createdDate;

    public FeedRegistDTO(Long feedNo, Long memberNo, String feedContent, String feedCategory, Date createdDate) {
        this.feedNo = feedNo;
        this.memberNo = memberNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.createdDate = createdDate;
    }

    public void setFeedNo(Long feedNo) {
        this.feedNo = feedNo;
    }

    public void setMemberNo(Long memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
