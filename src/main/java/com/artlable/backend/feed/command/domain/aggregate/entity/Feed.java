package com.artlable.backend.feed.command.domain.aggregate.entity;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.likes.command.domain.aggregate.entity.Likes;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.report.command.domain.aggregate.entity.Report;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_no")
    private Long feedNo;

    @Column
    private String feedContent;

    @Column
    private String feedCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @OneToMany(mappedBy = "feed",cascade = CascadeType.REMOVE)
    @OrderBy("fileNo asc")
    private List<Files> file;

    @OneToMany(mappedBy = "feed",cascade = CascadeType.REMOVE)
    @OrderBy("commentNo asc")
    private List<Comment> commentList;

    @Column
    private Boolean feedIsDeleted;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
    private List<Likes> likesList;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.REMOVE)
    private List<Report> reportList;

    @Builder
    public Feed(Long feedNo, String feedContent, String feedCategory, Member member, List<Files> file,
                List<Comment> commentList, Boolean feedIsDeleted, List<Likes> likesList, List<Report> reportList) {
        this.feedNo = feedNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.member = member;
        this.file = file;
        this.commentList = commentList;
        this.feedIsDeleted = feedIsDeleted;
        this.likesList = likesList;
        this.reportList = reportList;
    }

    //피드 내용 수정 (더티체킹)
    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    //피드 삭제 (softDelete)
    public void setFeedIsDeleted(Boolean feedIsDeleted){ this.feedIsDeleted = feedIsDeleted; }

    //글작성
    public void setMember(Member member) {
        this.member = member;
    }
}
