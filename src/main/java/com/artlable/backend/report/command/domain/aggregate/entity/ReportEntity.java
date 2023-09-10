package com.artlable.backend.report.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.FeedEntity;
import com.artlable.backend.member.command.domain.aggregate.entity.MemberEntity;
import com.artlable.backend.report.command.domain.aggregate.enumtype.ReportStateEnum;
import com.artlable.backend.report.command.domain.aggregate.enumtype.ReportTypeEnum;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Getter
@ToString
public class ReportEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private MemberEntity memberNo;

    @ManyToOne
    @JoinColumn(name = "feed_no")
    private FeedEntity feedNo;

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
}
