package com.artlable.backend.webtoon.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inference extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long inferenceNo;

    @Column
    private String inferenceContent;

    @Column
    private String inferenceResult;

    @Column Boolean inferenceIsDeleted;

    @OneToMany(mappedBy = "feed",cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> files;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Builder
    public Inference(Long inferenceNo, String inferenceContent, String inferenceResult, Boolean inferenceIsDeleted, List<Files> files, Member member) {
        this.inferenceNo = inferenceNo;
        this.inferenceContent = inferenceContent;
        this.inferenceResult = inferenceResult;
        this.inferenceIsDeleted = inferenceIsDeleted;
        this.files = files;
        this.member = member;
    }

    public void setInferenceNo(Long inferenceNo) {
        this.inferenceNo = inferenceNo;
    }

    public void setInferenceContent(String inferenceContent) {
        this.inferenceContent = inferenceContent;
    }

    public void setInferenceResult(String inferenceResult) {
        this.inferenceResult = inferenceResult;
    }

    public void setInferenceIsDeleted(Boolean inferenceIsDeleted) {
        this.inferenceIsDeleted = inferenceIsDeleted;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
