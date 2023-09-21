package com.artlable.backend.feed.command.application.dto.create;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedCreateRequestDTO {

    private String feedContent;
    private String feedCategory;
    private List<Files> files;

    @Builder
    public FeedCreateRequestDTO(String feedContent, String feedCategory, List<Files> files) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.files = files;
    }

    //DTO -> Entity
    public Feed toEntity(){
        return Feed.builder()
                .feedContent(feedContent)
                .feedCategory(feedCategory)
                .file(files)
                .feedIsDeleted(false)
                .build();
    }
}
