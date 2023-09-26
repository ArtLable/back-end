package com.artlable.backend.novel.command.application.dto.novelcharacter;

import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelResultCharacterDTO {

    private String originFileName;
    private String fileName;
    private String filePath;
    private Long memberNo;
    private Long resultNo;

    @Builder
    public NovelResultCharacterDTO(String originFileName, String fileName, String filePath, Long memberNo, Long resultNo){
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.memberNo = memberNo;
        this.resultNo = resultNo;
    }

    public Files toEntity(){
        return Files.builder()
                .originFileName(this.originFileName)
                .fileName(this.fileName)
                .filesIsDeleted(false)
                .filePath(this.filePath)
                .fileNo(this.resultNo)
                .member(Member.builder().memberNo(this.memberNo).build())
                .build();
    }



}
