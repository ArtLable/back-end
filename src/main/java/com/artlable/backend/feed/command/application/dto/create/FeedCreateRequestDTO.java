package com.artlable.backend.feed.command.application.dto.create;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.files.command.application.dto.FileRequestDTO;
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
    private List<Files> file;

    @Builder
    public FeedCreateRequestDTO(String feedContent, String feedCategory, List<Files> file) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.file = file;
    }

    //DTO -> Entity
    public Feed toEntity(){
        return Feed.builder()
                .feedContent(feedContent)
                .feedCategory(feedCategory)
                .file(file)
                .build();
    }
}
