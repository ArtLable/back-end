package com.artlable.backend.file.command.application.dto;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDTO {

    private Long fileNo;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String filePath;
    private Member memberNo;
//    private Feed feedNo;

    public FileDTO(Long fileNo, String fileName, String fileType, String fileSize, String filePath, Member memberNo, Feed feedNo) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.memberNo = memberNo;
//        this.feedNo = feedNo;
    }

    public void setFileNo(Long fileNo) {
        this.fileNo = fileNo;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

//    public void setFeedNo(Feed feedNo) {
//        this.feedNo = feedNo;
//    }
}
