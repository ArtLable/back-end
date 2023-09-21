package com.artlable.backend.files.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Files extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private Long fileNo;

    @Column
    private String originFileName;

    @Column
    private String fileName;

    @Column
    private boolean filesIsDeleted;

    @Column
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;

    @Builder
    public Files(Long fileNo, String originFileName, String fileName, boolean filesIsDeleted,
                 String filePath, Member member, Feed feed) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.originFileName = originFileName;
        this.filesIsDeleted = filesIsDeleted;
        this.filePath = filePath;
        this.member = member;
        this.feed = feed;
    }

    public void setMemberNo(Member member) {
        this.member = member;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

}
