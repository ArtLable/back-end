package com.artlable.backend.comment.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@ToString
public class Comment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    @ManyToOne
    @JoinColumn(name = "feed_No")
    private Feed feedNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @Column(name = "comment_content", length = 100)
    private String commentContent;

    private boolean commentIsDeleted;

    public Comment() {

    }

    public Comment(Long commentNo, Feed feedNo, Member memberNo, String commentContent, boolean commentIsDeleted) {
        this.commentNo = commentNo;
        this.feedNo = feedNo;
        this.memberNo = memberNo;
        this.commentContent = commentContent;
        this.commentIsDeleted = commentIsDeleted;
    }

    public void setCommentNo(Long commentNo) {
        this.commentNo = commentNo;
    }

    public void setFeedNo(Feed feedNo) {
        this.feedNo = feedNo;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentIsDeleted(boolean commentIsDeleted) {
        this.commentIsDeleted = commentIsDeleted;
    }
}
