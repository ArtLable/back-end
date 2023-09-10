package com.artlable.backend.comment.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.FeedEntity;
import com.artlable.backend.member.command.domain.aggregate.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@ToString
public class CommentEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    @ManyToOne
    @JoinColumn(name = "feed_No")
    private FeedEntity feedNo;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private MemberEntity memberNo;

    @Column(name = "comment_content", length = 100)
    private String commentContent;

    private boolean commentIsDeleted;

    public CommentEntity() {

    }

    public CommentEntity(Long commentNo, FeedEntity feedNo, MemberEntity memberNo, String commentContent, boolean commentIsDeleted) {
        this.commentNo = commentNo;
        this.feedNo = feedNo;
        this.memberNo = memberNo;
        this.commentContent = commentContent;
        this.commentIsDeleted = commentIsDeleted;
    }

    public void setCommentNo(Long commentNo) {
        this.commentNo = commentNo;
    }

    public void setFeedNo(FeedEntity feedNo) {
        this.feedNo = feedNo;
    }

    public void setMemberNo(MemberEntity memberNo) {
        this.memberNo = memberNo;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentIsDeleted(boolean commentIsDeleted) {
        this.commentIsDeleted = commentIsDeleted;
    }
}
