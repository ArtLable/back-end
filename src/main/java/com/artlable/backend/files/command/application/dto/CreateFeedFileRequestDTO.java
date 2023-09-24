package com.artlable.backend.files.command.application.dto;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateFeedFileRequestDTO {

    private String originFileName;

    private String fileName;

    private String filePath;

    private Long memberNo;

    private Long feedNo;

    @Builder
    public CreateFeedFileRequestDTO(String originFileName, String fileName, String filePath, Long memberNo, Long feedNo){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.memberNo = memberNo;
        this.feedNo = feedNo;
    }

    public CreateFeedFileRequestDTO(String originFileName, String fileName, String filePath){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public Files toEntity(){
        return Files.builder()
                .originFileName(this.originFileName)
                .fileName(this.fileName)
                .filesIsDeleted(false)
                .filePath(this.filePath)
                .feed(Feed.builder().feedNo(this.feedNo).build())
                .member(Member.builder().memberNo(this.memberNo).build())
                .build();
    }
}
