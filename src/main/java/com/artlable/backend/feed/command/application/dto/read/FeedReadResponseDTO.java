package com.artlable.backend.feed.command.application.dto.read;

import com.artlable.backend.comment.command.domain.aggregate.entity.Comment;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.file.command.domain.aggregate.entity.File;
import com.artlable.backend.like.command.domain.aggregate.entity.Like;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedReadResponseDTO {

    private Long feedNo;
    private String memberNickname;
    private String feedContent;
    private String feedCategory;
    private List<Long> fileNo;
    private List<Long> commentNo;
    private List<Long> likeNo;

    //조회 매핑
    public FeedReadResponseDTO(Feed feed){
        this.feedNo = feed.getFeedNo();
        this.memberNickname = feed.getMember().getMemberNickname();
        this.feedContent = feed.getFeedContent();
        this.feedCategory = feed.getFeedCategory();
        this.fileNo = feed.getFile().stream().map(File::getFileNo)
                .collect(Collectors.toList());
        this.commentNo = feed.getCommentList().stream().map(Comment::getCommentNo)
                .collect(Collectors.toList());
        this.likeNo = feed.getLikeList().stream().map(Like::getLikeNo)
                .collect(Collectors.toList());

    }

    @Builder
    public FeedReadResponseDTO(Long feedNo, String memberNickname, String feedContent, String feedCategory, List<Long> fileNo,
                                List<Long> commentNo, List<Long> likeNo) {
        this.feedNo = feedNo;
        this.memberNickname = memberNickname;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.fileNo = fileNo;
        this.commentNo = commentNo;
        this.likeNo = likeNo;
    }
}
