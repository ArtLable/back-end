package com.artlable.backend.report.command.domain.aggregate.entity;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.report.command.domain.aggregate.enumtype.ReportStateEnum;
import com.artlable.backend.report.command.domain.aggregate.enumtype.ReportTypeEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member reporter; //신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member reportee; //피신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_no")
    private Feed feed; // 신고당한 컨텐츠

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no")
    private Comment comment; // 신고당한 댓글

    @Column
    @Enumerated(EnumType.STRING)
    private ReportStateEnum reportState;

    @Column
    @Enumerated(EnumType.STRING)
    private ReportTypeEnum reportType;

    public Report(Long reportNo, Member reporter, Member reportee, Feed feed, Comment comment, ReportStateEnum reportState, ReportTypeEnum reportType) {
        this.reportNo = reportNo;
        this.reporter = reporter;
        this.reportee = reportee;
        this.feed = feed;
        this.comment = comment;
        this.reportState = reportState;
        this.reportType = reportType;
    }
}
