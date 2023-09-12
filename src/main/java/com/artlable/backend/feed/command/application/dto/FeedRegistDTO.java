package com.artlable.backend.feed.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
public class FeedRegistDTO {

    private Long feedNo;
    private String feedContent;
    private String feedCategory;
    private LocalDateTime createdDate;

    public FeedRegistDTO(Long feedNo, String feedContent, String feedCategory, LocalDateTime createdDate) {
        this.feedNo = feedNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.createdDate = createdDate;
    }

    public void setFeedNo(Long feedNo) {
        this.feedNo = feedNo;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
