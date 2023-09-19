package com.artlable.backend.webtoon.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.file.command.domain.aggregate.entity.File;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "web_toon")
@Getter
@ToString
public class WebToon extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "web_toon_no")
    private Long webToonNo;

    @Column(name = "web_toon_content")
    private String webToonContent;

    @Column(name = "web_toon_title")
    private String webToonTitle;

    @Column(name = "web_toon_type")
    private String webToonType;

    @Column(name = "character_name")
    private String characterName;

//    @OneToMany
//    @JoinColumn(name = "file_no")
//    private List<File> fileNo;
//
//    @OneToMany
//    @JoinColumn(name = "file_name")
//    private List<File>  fileName;
//
//    @OneToMany
//    @JoinColumn(name = "file_path")
//    private List<File> filePath;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    public WebToon(Long webToonNo, String webToonContent, String webToonTitle, String webToonType, String characterName,
                   List<File> fileNo, List<File> fileName, List<File> filePath, Member memberNo) {
        this.webToonNo = webToonNo;
        this.webToonContent = webToonContent;
        this.webToonTitle = webToonTitle;
        this.webToonType = webToonType;
        this.characterName = characterName;
//        this.fileNo = fileNo;
//        this.fileName = fileName;
//        this.filePath = filePath;
        this.memberNo = memberNo;
    }

    public WebToon() {

    }

    public void setWebToonNo(Long webToonNo) {
        this.webToonNo = webToonNo;
    }

    public void setWebToonContent(String webToonContent) {
        this.webToonContent = webToonContent;
    }

    public void setWebToonTitle(String webToonTitle) {
        this.webToonTitle = webToonTitle;
    }

    public void setWebToonType(String webToonType) {
        this.webToonType = webToonType;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

//    public void setFileNo(List<File> fileNo) {
//        this.fileNo = fileNo;
//    }
//
//    public void setFileName(List<File> fileName) {
//        this.fileName = fileName;
//    }
//
//    public void setFilePath(List<File> filePath) {
//        this.filePath = filePath;
//    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }
}
