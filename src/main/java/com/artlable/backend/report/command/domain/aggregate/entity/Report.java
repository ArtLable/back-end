package com.artlable.backend.report.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.report.command.domain.aggregate.enumtype.ReportStateEnum;
import com.artlable.backend.report.command.domain.aggregate.enumtype.ReportTypeEnum;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Getter
@ToString
public class Report extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "feed_no")
    private Feed feedNo;

    @Column
    private String reporter;

    @Column
    private String reportee;

    @Column(name = "report_state")
    @Enumerated(EnumType.STRING)
    private ReportStateEnum reportState;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private ReportTypeEnum reportType;

    public Report() {

    }

    public Report(Long reportNo, Member memberNo, Feed feedNo, String reporter, String reportee, ReportStateEnum reportState, ReportTypeEnum reportType) {
        this.reportNo = reportNo;
        this.memberNo = memberNo;
        this.feedNo = feedNo;
        this.reporter = reporter;
        this.reportee = reportee;
        this.reportState = reportState;
        this.reportType = reportType;
    }

    public void setReportNo(Long reportNo) {
        this.reportNo = reportNo;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedNo(Feed feedNo) {
        this.feedNo = feedNo;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public void setReportee(String reportee) {
        this.reportee = reportee;
    }

    public void setReportState(ReportStateEnum reportState) {
        this.reportState = reportState;
    }

    public void setReportType(ReportTypeEnum reportType) {
        this.reportType = reportType;
    }
}
