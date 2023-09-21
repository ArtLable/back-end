package com.artlable.backend.file.command.domain.aggregate.vo;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberVo {

    @Column
    private Long memberNo;
}
