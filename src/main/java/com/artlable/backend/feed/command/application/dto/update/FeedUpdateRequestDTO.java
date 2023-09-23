package com.artlable.backend.feed.command.application.dto.update;

import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedUpdateRequestDTO {

    private String feedContent;
    private List<FileRequestDTO> filesList;

    public FeedUpdateRequestDTO(String feedContent, List<FileRequestDTO> filesList) {
        this.feedContent = feedContent;
        this.filesList = filesList;
    }
}
