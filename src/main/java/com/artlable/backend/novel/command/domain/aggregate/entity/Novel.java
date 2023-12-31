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
public class Novel extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "novel_no")
    private Long novelNo;

    @Column(name = "novel_content")
    private String novelContent;  // 소설 줄거리

    @Column(name = "novel_title")  // 소설 이름, 제목
    private String novelTitle;

    @Column(name = "novel_genre")  // 소설 장르
    private String novelGenre;

    @Column
    private Boolean novelIsDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.REMOVE)
    @OrderBy("characterNo asc")
    private List<NovelCharacter> characters;

    @OneToMany(mappedBy = "novel", cascade = CascadeType.REMOVE)
    @OrderBy("summaryNo asc")
    private List<NovelSummary> summaries;

    @OneToMany(mappedBy = "novel",cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> files;

    @Builder
    public Novel(Long novelNo, String novelContent, String novelTitle, String novelGenre, Boolean novelIsDeleted,
                 Member member, List<NovelCharacter> characters, List<NovelSummary> summaries, List<Files> files) {
        this.novelNo = novelNo;
        this.novelContent = novelContent;
        this.novelTitle = novelTitle;
        this.novelGenre = novelGenre;
        this.novelIsDeleted = novelIsDeleted;
        this.member = member;
        this.characters = characters;
        this.summaries = summaries;
        this.files = files;
    }

    public void setNovelNo(Long novelNo) {
        this.novelNo = novelNo;
    }

    public void setNovelContent(String novelContent) {
        this.novelContent = novelContent;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public void setNovelGenre(String novelGenre) {
        this.novelGenre = novelGenre;
    }

    public void setNovelIsDeleted(Boolean novelIsDeleted) {
        this.novelIsDeleted = novelIsDeleted;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setCharacterList(List<NovelCharacter> characters) {
        this.characters = characters;
    }

    public void setSummaryList(List<NovelSummary> summaries) {
        this.summaries = summaries;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }
    public void addCharacter(NovelCharacter character) {
        this.characters.add(character);
        character.setNovel(this); // 양방향 연관관계 설정
    }
    public void addSummary(NovelSummary summary) {
        this.summaries.add(summary);
        summary.setNovel(this); // 양방향 연관관계 설정
    }
}
