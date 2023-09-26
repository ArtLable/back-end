package com.artlable.backend.feed.command.application.dto.create;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedCreateRequestDTO {

    private String feedContent;
    private String feedCategory;
    private List<MultipartFile> files;

    @Builder
    public FeedCreateRequestDTO(String feedContent, String feedCategory, List<MultipartFile> files) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.files = files;
    }

    //DTO -> Entity
    public Feed toEntity(){
        return Feed.builder()
                .feedContent(feedContent)
                .feedCategory(feedCategory)
                .feedIsDeleted(false)
                .build();
    }
}
