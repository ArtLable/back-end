package com.artlable.backend.feed.command.domain.aggregate.entity;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.file.command.domain.aggregate.entity.File;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "feed")
@Getter
@ToString
public class Feed extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_no")
    private Long feedNo;

    @Column(name = "feed_content")
    private String feedContent;

    @Column(name = "feed_category")
    private String feedCategory;

    private boolean feedIsDeleted;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @OneToMany
    @JoinColumn
    @OrderBy("fileNo asc")
    private List<File> fileNo;

    @OneToMany
    @JoinColumn
    @OrderBy("commentNo asc")
    private List<Comment> commentList;

    private boolean commentIsDeleted;

    @Column
    private int likeCount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Feed() {

    }

    public Feed(Long feedNo, String feedContent, String feedCategory, boolean feedIsDeleted, Member memberNo, List<File> fileNo, List<Comment> commentList, boolean commentIsDeleted, int likeCount, LocalDateTime createdDate) {
        this.feedNo = feedNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.feedIsDeleted = feedIsDeleted;
        this.memberNo = memberNo;
        this.fileNo = fileNo;
        this.commentList = commentList;
        this.commentIsDeleted = commentIsDeleted;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
    }

    public void create(Long newFeedNo, String newFeedContent, String newFeedCategory, LocalDateTime newCreatedDate) {
        this.feedNo = newFeedNo;
        this.feedContent = newFeedContent;
        this.feedCategory = newFeedCategory;
        this.createdDate = newCreatedDate;

    }
    public void update(String feedContent, String feedCategory, LocalDateTime createdDate) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.createdDate = createdDate;
    }

    public void delete() {
        this.feedIsDeleted = true;
    }

    public void setFeedNo(Long feedNo) {
        this.feedNo = feedNo;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    public void setFeedIsDeleted(boolean feedIsDeleted) {
        this.feedIsDeleted = feedIsDeleted;
    }

    public void setMemberNo(Member memberNo) {
        this.memberNo = memberNo;
    }

    public void setFileNo(List<File> fileNo) {
        this.fileNo = fileNo;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setCommentIsDeleted(boolean commentIsDeleted) {
        this.commentIsDeleted = commentIsDeleted;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
