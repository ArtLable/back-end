package com.artlable.backend.like.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@ToString
public class Like extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long likeNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne
    @JoinColumn(name = "feed_no")
    private Feed feedNo;

    public Like() {

    }

    public Like(Long likeNo, Member memberNo, Feed feedNo) {
        this.likeNo = likeNo;
        this.memberNo = memberNo;
        this.feedNo = feedNo;
    }

    public void setLikeNo(Long likeNo) {
        this.likeNo = likeNo;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedNo(Feed feedNo) {
        this.feedNo = feedNo;
    }
}
