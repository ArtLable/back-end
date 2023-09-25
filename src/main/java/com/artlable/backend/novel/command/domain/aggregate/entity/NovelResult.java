//package com.artlable.backend.novel.command.domain.aggregate.entity;
//
//import com.artlable.backend.common.AuditingFields;
//import com.artlable.backend.files.command.domain.aggregate.entity.Files;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class NovelResult extends AuditingFields {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long resultNo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "novel_no")
//    private Novel novel;
//
//    @OneToMany(mappedBy = "novel",cascade = CascadeType.REMOVE)
//    @OrderBy("resultNo asc")
//    private List<NovelResult> novelResult;
//
//    @OneToMany(mappedBy = "novel",cascade = CascadeType.REMOVE)
//    @OrderBy("characterNo asc")
//    private List<NovelCharacter> novelCharacter;
//
//    @OneToMany(mappedBy = "novel",cascade = CascadeType.REMOVE)
//    @OrderBy("summaryNo asc")
//    private List<NovelSummary> novelSummary;
//
//    @OneToMany(mappedBy = "novel",cascade = CascadeType.REMOVE)
//    @OrderBy("fileNo asc")
//    private List<Files> files;
//
//    public NovelResult(Long resultNo, Novel novel, List<NovelResult> novelResult, List<NovelCharacter> novelCharacter, List<NovelSummary> novelSummary) {
//        this.resultNo = resultNo;
//        this.novel = novel;
//        this.novelResult = novelResult;
//        this.novelCharacter = novelCharacter;
//        this.novelSummary = novelSummary;
//    }
//
//    public void setResultNo(Long resultNo) {
//        this.resultNo = resultNo;
//    }
//
//    public void setNovel(Novel novel) {
//        this.novel = novel;
//    }
//
//    public void setNovelResult(List<NovelResult> novelResult) {
//        this.novelResult = novelResult;
//    }
//
//    public void setNovelCharacter(List<NovelCharacter> novelCharacter) {
//        this.novelCharacter = novelCharacter;
//    }
//
//    public void setNovelSummary(List<NovelSummary> novelSummary) {
//        this.novelSummary = novelSummary;
//    }
//
//    public void setFiles(List<Files> files) {
//        this.files = files;
//    }
//}
