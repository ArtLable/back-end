package com.artlable.backend.feed.command.application.dto.read;

import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.files.command.application.dto.feed.ReadFeedFileResponseDTO;
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
    private List<ReadFeedFileResponseDTO> files;
//    private List<Long> commentNo;
//    private List<Long> likesNo;

    //조회 매핑
    public FeedReadResponseDTO(Feed feed){
        this.feedNo = feed.getFeedNo();
        this.memberNickname = feed.getMember().getMemberNickname();
        this.feedContent = feed.getFeedContent();
        this.feedCategory = feed.getFeedCategory();
        this.files = feed.getFiles().stream()
                .map(ReadFeedFileResponseDTO::fromEntity)
                .collect(Collectors.toList());
//        this.commentNo = feed.getCommentList().stream().map(Comment::getCommentNo)
//                .collect(Collectors.toList());
//        this.likesNo = feed.getLikesList().stream().map(Likes::getLikeNo)
//                .collect(Collectors.toList());
    }

    @Builder
    public FeedReadResponseDTO(Long feedNo, String memberNickname, String feedContent, String feedCategory,
                               List<ReadFeedFileResponseDTO> files) {
        this.feedNo = feedNo;
        this.memberNickname = memberNickname;
        this.feedContent = feedContent;
        this.feedCategory = feedCategory;
        this.files = files;
//        this.commentNo = commentNo;
//        this.likesNo = likesNo;
    }
}
