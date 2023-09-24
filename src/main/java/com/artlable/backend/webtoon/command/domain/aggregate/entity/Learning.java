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
public class Learning extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long learningNo;

    @Column
    private String learningContent;

    @Column
    private Boolean learningIsDeleted;

    @OneToMany(mappedBy = "feed",cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> files;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Builder
    public Learning(Long learningNo, String learningContent, Boolean learningIsDeleted, List<Files> files, Member member) {
        this.learningNo = learningNo;
        this.learningContent = learningContent;
        this.learningIsDeleted = learningIsDeleted;
        this.files = files;
        this.member = member;
    }

    public void setLearningNo(Long learningNo) {
        this.learningNo = learningNo;
    }

    public void setLearningContent(String learningContent) {
        this.learningContent = learningContent;
    }

    public void setLearningIsDeleted(Boolean learningIsDeleted) {
        this.learningIsDeleted = learningIsDeleted;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
