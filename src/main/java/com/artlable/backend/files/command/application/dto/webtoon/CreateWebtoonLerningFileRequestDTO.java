package com.artlable.backend.files.command.application.dto.webtoon;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateWebtoonLerningFileRequestDTO {

    private Long fileNo;

    private String originFileName;

    private String fileName;

    private String filePath;

    private Long memberNo;

    private Long InputLearningNo;


    @Builder
    public CreateWebtoonLerningFileRequestDTO(Long fileNo, String originFileName, String fileName, String filePath,
                                              Long memberNo, Long InputLearningNo){
        this.fileNo = fileNo;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.memberNo = memberNo;
        this.InputLearningNo = InputLearningNo;
    }

    public Files toEntity(){
        return Files.builder()
                .fileNo(this.fileNo)
                .originFileName(this.originFileName)
                .fileName(this.fileName)
                .filesIsDeleted(false)
                .filePath(this.filePath)
                .member(Member.builder().memberNo(this.memberNo).build())
                .inputLearning(Learning.builder().learningNo(this.InputLearningNo).build())
                .build();
    }
}
