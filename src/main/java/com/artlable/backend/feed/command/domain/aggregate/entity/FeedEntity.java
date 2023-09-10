package com.artlable.backend.feed.command.domain.aggregate.entity;

import com.artlable.backend.comment.command.domain.aggregate.entity.CommentEntity;
import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.file.command.domain.aggregate.entity.FileEntity;
import com.artlable.backend.member.command.domain.aggregate.entity.MemberEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "feed")
@Getter
@ToString
public class FeedEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_no")
    private Long feedNo;

    @Column(name = "feed_category")
    private String feedCategory;

    private boolean feedIsDeleted;

    @ManyToOne
    @JoinColumn(name = "member_no")
    private MemberEntity memberNo;

    @OneToMany
    @JoinColumn
    @OrderBy("fileNo asc")
    private List<FileEntity> fileNo;

    @OneToMany
    @JoinColumn
    @OrderBy("commentNo asc")
    private List<CommentEntity> commentList;

    private boolean commentIsDeleted;

    @Column
    private int likeCount;

    public FeedEntity() {

    }

    public FeedEntity(Long feedNo, String feedCategory, boolean feedIsDeleted, MemberEntity memberNo, List<FileEntity> fileNo, List<CommentEntity> commentList, boolean commentIsDeleted, int likeCount) {
        this.feedNo = feedNo;
        this.feedCategory = feedCategory;
        this.feedIsDeleted = feedIsDeleted;
        this.memberNo = memberNo;
        this.fileNo = fileNo;
        this.commentList = commentList;
        this.commentIsDeleted = commentIsDeleted;
        this.likeCount = likeCount;
    }
}
