package com.artlable.backend.files.command.application.dto.novel;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateNovelSummaryFileRequestDTO {

    private String originFileName;

    private String fileName;

    private String filePath;

    private Long memberNo;

    private Long summaryNo;

    @Builder
    public CreateNovelSummaryFileRequestDTO(String originFileName, String fileName, String filePath, Long memberNo, Long summaryNo){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.memberNo = memberNo;
        this.summaryNo = summaryNo;
    }

    public Files toEntity(){
        return Files.builder()
                .originFileName(this.originFileName)
                .fileName(this.fileName)
                .filesIsDeleted(false)
                .filePath(this.filePath)
                .novelSummary(NovelSummary.builder().summaryNo(this.summaryNo).build())
                .member(Member.builder().memberNo(this.memberNo).build())
                .build();
    }
}
