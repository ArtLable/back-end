package com.artlable.backend.feed.command.application.dto;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class FeedUpdateDTO {

    private String feedContent;
    private String feedCategory;

    public FeedUpdateDTO(String feedContent, String feedCategory) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }
}
