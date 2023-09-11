package com.artlable.backend.feed.command.application.dto;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import com.artlable.backend.file.command.domain.aggregate.entity.File;
import com.artlable.backend.like.command.domain.aggregate.entity.Like;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class FeedListDTO {

    private Long feedNo;
    private Long memberNo;
    private String feedContent;
    private String feedCategory;
    private List<Comment> commentList;
    private List<File> fileNo;
    private List<Like> likeNo;
    private LocalDateTime createdDate;

    public FeedListDTO(Long feedNo, Long memberNo, String feedContent, String feedCategory, List<Comment> commentList, List<File> fileNo, List<Like> likeNo, LocalDateTime createdDate) {
        this.feedNo = feedNo;
        this.memberNo = memberNo;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.commentList = commentList;
        this.fileNo = fileNo;
        this.likeNo = likeNo;
        this.createdDate = createdDate;
    }

    public void setFeedNo(Long feedNo) {
        this.feedNo = feedNo;
    }

    public void setMemberNo(Long memberNo) {
        this.memberNo = memberNo;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public void setFeedCategory(String feedCategory) {
        this.feedCategory = feedCategory;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setFileNo(List<File> fileNo) {
        this.fileNo = fileNo;
    }

    public void setLikeNo(List<Like> likeNo) {
        this.likeNo = likeNo;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
