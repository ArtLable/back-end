package com.artlable.backend.files.command.application.dto.webtoon;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadNovelFileRequestDTO {

    private String originFileName;

    private String fileName;

    private String filePath;

    private Long memberNo;

    private Long novelNo;

    @Builder
    public ReadNovelFileRequestDTO(String originFileName, String fileName, String filePath, Long memberNo, Long novelNo){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.memberNo = memberNo;
        this.novelNo = novelNo;
    }

    public ReadNovelFileRequestDTO(String originFileName, String fileName, String filePath){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static ReadNovelFileRequestDTO fromEntity(Files files) {
        return ReadNovelFileRequestDTO.builder()
                .originFileName(files.getOriginFileName())
                .fileName(files.getFileName())
                .filePath(files.getFilePath())
                .build();
    }
}
