package com.artlable.backend.feed.command.domain.aggregate.entity;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.application.dto.FeedListDTO;
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

    @Column(name = "feed_is_deleted")
    private boolean feedIsDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

//    @OneToMany
//    @JoinColumn(name = "file_no")
//    @OrderBy("fileNo asc")
//    private List<File> fileNo;

//    @OneToMany
//    @JoinColumn(name = "comment_no")
//    @OrderBy("commentNo asc")
//    private List<Comment> commentList;

    @Column(name = "comment_is_deleted")
    private boolean commentIsDeleted;

    @Column(name = "like_count")
    private int likeCount;

    public Feed() {

    }

    public Feed(Long feedNo, String feedContent, String feedCategory, boolean feedIsDeleted, Member member, List<File> fileNo,
                List<Comment> commentList, boolean commentIsDeleted, int likeCount) {
        this.feedNo = feedNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.feedIsDeleted = feedIsDeleted;
        this.member = member;
//        this.fileNo = fileNo;
//        this.commentList = commentList;
        this.commentIsDeleted = commentIsDeleted;
        this.likeCount = likeCount;
    }

    public void create(Long newFeedNo, String newFeedContent, String newFeedCategory) {
        this.feedNo = newFeedNo;
        this.feedContent = newFeedContent;
        this.feedCategory = newFeedCategory;

    }
    public void update(String feedContent, String feedCategory) {
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
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

    public void setMember(Member member) {
        this.member = member;
    }

//    public void setFileNo(List<File> fileNo) {
//        this.fileNo = fileNo;
//    }

//    public void setCommentList(List<Comment> commentList) {
//        this.commentList = commentList;
//    }

    public void setCommentIsDeleted(boolean commentIsDeleted) {
        this.commentIsDeleted = commentIsDeleted;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
