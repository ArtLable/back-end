package com.artlable.backend.noble.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "noble")
@Getter
@ToString
public class Noble extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noble_no")
    private Long nobleNo;

    @Column(name = "noble_content")
    private String nobleContent;

    @Column(name = "noble_title")
    private String noble_title;

    @Column(name = "noble_type")
    private String noble_type;

    @Column(name = "character_name")
    private String character_name;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

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

    public Noble(Long nobleNo, String nobleContent, String noble_title, String noble_type,
                 String character_name, List<Files> fileNo, List<Files> fileName, List<Files> filePath, Member memberNo) {
        this.nobleNo = nobleNo;
        this.nobleContent = nobleContent;
        this.noble_title = noble_title;
        this.noble_type = noble_type;
        this.character_name = character_name;
//        this.fileNo = fileNo;
//        this.fileName = fileName;
//        this.filePath = filePath;
        this.memberNo = memberNo;
    }

    public Noble() {

    }

    public void setNobleNo(Long nobleNo) {
        this.nobleNo = nobleNo;
    }

    public void setNobleContent(String nobleContent) {
        this.nobleContent = nobleContent;
    }

    public void setNoble_title(String noble_title) {
        this.noble_title = noble_title;
    }

    public void setNoble_type(String noble_type) {
        this.noble_type = noble_type;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
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
