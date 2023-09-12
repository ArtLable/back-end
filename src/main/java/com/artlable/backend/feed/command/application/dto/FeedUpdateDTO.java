package com.artlable.backend.feed.command.application.dto;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FeedUpdateDTO {

    private String feedContent;
    private String feedCategory;
    private LocalDateTime createdDate;

    public FeedUpdateDTO(String feedContent, String feedCategory, LocalDateTime createdDate) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.createdDate = createdDate;
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
