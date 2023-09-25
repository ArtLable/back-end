package com.artlable.backend.files.command.application.dto.feed;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadFeedFileResponseDTO {

    private String originFileName;
    private String fileName;
    private String filePath;

    @Builder
    public ReadFeedFileResponseDTO(String originFileName, String fileName, String filePath) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static ReadFeedFileResponseDTO fromEntity(Files files) {
        return ReadFeedFileResponseDTO.builder()
                .originFileName(files.getOriginFileName())
                .fileName(files.getFileName())
                .filePath(files.getFilePath())
                .build();
    }
}