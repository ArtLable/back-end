package com.artlable.backend.novel.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelSummary extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long summaryNo;

    @Column
    private String summaryContent;

    @Column
    private String summaryResult;

    @Column
    private Boolean summaryIsDeleted;

    @OneToMany(mappedBy = "feed",cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Builder
    public NovelSummary(Long summaryNo, String summaryContent, String summaryResult, Boolean summaryIsDeleted, List<Files> files, Member member) {
        this.summaryNo = summaryNo;
        this.summaryContent = summaryContent;
        this.summaryResult = summaryResult;
        this.summaryIsDeleted = summaryIsDeleted;
        this.files = files;
        this.member = member;
    }

    public void setSummaryNo(Long summaryNo) {
        this.summaryNo = summaryNo;
    }

    public void setSummaryContent(String summaryContent) {
        this.summaryContent = summaryContent;
    }

    public void setSummaryResult(String summaryResult) {
        this.summaryResult = summaryResult;
    }

    public void setSummaryIsDeleted(Boolean summaryIsDeleted) {
        this.summaryIsDeleted = summaryIsDeleted;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
