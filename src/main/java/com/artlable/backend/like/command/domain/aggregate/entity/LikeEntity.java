package com.artlable.backend.like.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.FeedEntity;
import com.artlable.backend.member.command.domain.aggregate.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@ToString
public class LikeEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long likeNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private MemberEntity memberNo;

    @ManyToOne
    @JoinColumn(name = "feed_no")
    private FeedEntity feedNo;

    public LikeEntity() {

    }

    public LikeEntity(Long likeNo, MemberEntity memberNo, FeedEntity feedNo) {
        this.likeNo = likeNo;
        this.memberNo = memberNo;
        this.feedNo = feedNo;
    }

    public void setLikeNo(Long likeNo) {
        this.likeNo = likeNo;
    }

    public void setMemberNo(MemberEntity memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedNo(FeedEntity feedNo) {
        this.feedNo = feedNo;
    }
}
