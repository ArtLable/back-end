package com.artlable.backend.noble.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NovelCharacter extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long characterNo;

    @Column
    private String characterName;

    @Column
    private String characterGender;

    @Column
    private String characterAppearance;

    @Column
    private String characterPersonality;

    @Column
    private String characterResult;

    @OneToMany(mappedBy = "feed",cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Builder
    public NovelCharacter(Long characterNo, String characterName, String characterGender, String characterAppearance,
                          String characterPersonality, String characterResult, List<Files> files, Member member) {
        this.characterNo = characterNo;
        this.characterName = characterName;
        this.characterGender = characterGender;
        this.characterAppearance = characterAppearance;
        this.characterPersonality = characterPersonality;
        this.characterResult = characterResult;
        this.files = files;
        this.member = member;
    }

    public void setCharacterNo(Long characterNo) {
        this.characterNo = characterNo;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setCharacterGender(String characterGender) {
        this.characterGender = characterGender;
    }

    public void setCharacterAppearance(String characterAppearance) {
        this.characterAppearance = characterAppearance;
    }

    public void setCharacterPersonality(String characterPersonality) {
        this.characterPersonality = characterPersonality;
    }

    public void setCharacterResult(String characterResult) {
        this.characterResult = characterResult;
    }

    public void setFileNo(List<Files> files) {
        this.files = files;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}