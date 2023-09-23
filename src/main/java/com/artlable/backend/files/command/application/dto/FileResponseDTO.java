package com.artlable.backend.files.command.application.dto;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileResponseDTO {

    private String originFileName;

    private String fileName;

    private String filePath;

    private Member member;

    private Feed feed;

    public static FileResponseDTO fromEntity(Files files) {
        FileResponseDTO fileResponseDTO = new FileResponseDTO();
        fileResponseDTO.setOriginFileName(files.getOriginFileName());
        fileResponseDTO.setFileName(files.getFileName());
        fileResponseDTO.setFilePath(files.getFilePath());
        fileResponseDTO.setMember(files.getMember());
        fileResponseDTO.setFeed(files.getFeed());

        return fileResponseDTO;
    }

    @Builder
    public FileResponseDTO(String originFileName, String fileName, String filePath, Member member, Feed feed){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.member = member;
        this.feed = feed;
    }
}
