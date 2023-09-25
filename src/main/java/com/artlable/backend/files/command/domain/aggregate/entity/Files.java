package com.artlable.backend.files.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
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
    private String originFileName; //원래이름

    @Column
    private String fileName; //저장된 이름

    @Column
    private Boolean filesIsDeleted; //조회불가처리

    @Column
    private String filePath; //경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_no")
    private Novel novel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novelCharacter_no")
    private NovelCharacter novelCharacter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novelSummary_no")
    private NovelSummary novelSummary;

    @Builder
    public Files(Long fileNo, String originFileName, String fileName, Boolean filesIsDeleted,
                 String filePath, Member member, Feed feed, Novel novel, NovelCharacter novelCharacter, NovelSummary novelSummary) {
        this.fileNo = fileNo;
        this.fileName = fileName;
        this.originFileName = originFileName;
        this.filesIsDeleted = filesIsDeleted;
        this.filePath = filePath;
        this.member = member;
        this.feed = feed;
        this.novel = novel;
        this.novelCharacter = novelCharacter;
        this.novelSummary = novelSummary;
    }

    public void setMemberNo(Member member) {
        this.member = member;
    }

    //피드생성
    public void setFeed(Feed feed) {
        this.feed = feed;
    }

}
