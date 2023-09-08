package com.artlable.backend.feed.command.domain.aggregate.entity;

import com.artlable.backend.comment.command.domain.aggregate.entity.CommentEntity;
import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.file.command.domain.aggregate.entity.FileEntity;
import com.artlable.backend.member.command.domain.aggregate.entity.MemberEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "feed")
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
}
