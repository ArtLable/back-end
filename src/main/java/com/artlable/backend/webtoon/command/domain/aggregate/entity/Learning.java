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
    private String cname;

    @Column
    private String searchText;

    @Column
    private Boolean learningIsDeleted;

    @OneToMany(mappedBy = "inputLearning", cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> inputFiles;

    @OneToMany(mappedBy = "resultLearning", cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> resultFiles;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Builder
    public Learning(Long learningNo, String canme ,String searchText,  Boolean learningIsDeleted, List<Files> inputFiles,
                    List<Files> resultFiles, Member member) {
        this.learningNo = learningNo;
        this.cname = canme;
        this.searchText = searchText;
        this.learningIsDeleted = learningIsDeleted;
        this.inputFiles = inputFiles;
        this.resultFiles = resultFiles;
        this.member = member;
    }

    public void setLearningNo(Long learningNo) {
        this.learningNo = learningNo;
    }

    public void setLearningIsDeleted(Boolean learningIsDeleted) {
        this.learningIsDeleted = learningIsDeleted;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setFiles(List<Files> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
