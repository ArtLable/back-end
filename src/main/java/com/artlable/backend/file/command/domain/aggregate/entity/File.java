package com.artlable.backend.file.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@ToString
public class File extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Long fileNo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "feed_No")
    private Feed feedNo;

    public File() {

    }

    public File(Long fileNo, String fileName, String filePath, Member memberNo, Feed feedNo) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.filePath = filePath;
        this.memberNo = memberNo;
        this.feedNo = feedNo;
    }

    public void setFileNo(Long fileNo) {
        this.fileNo = fileNo;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedNo(Feed feedNo) {
        this.feedNo = feedNo;
    }
}
