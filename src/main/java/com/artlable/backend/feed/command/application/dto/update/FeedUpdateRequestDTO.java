package com.artlable.backend.feed.command.application.dto.update;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedUpdateRequestDTO {

    private String feedContent;

    public FeedUpdateRequestDTO(String feedContent) {
        this.feedContent = feedContent;
    }

}
