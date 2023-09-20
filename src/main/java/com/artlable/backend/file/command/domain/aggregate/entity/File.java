package com.artlable.backend.file.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Long fileNo;

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Column
    private Long fileSize;

    @Column
    private String filePath;

    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;

    @Builder
    public File(Long fileNo, String fileName, String fileType, Long fileSize, String filePath, Member member, Feed feed) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.member = member;
        this.feed = feed;
    }

    public void setMemberNo(Member member) {
        this.member = member;
    }

}
