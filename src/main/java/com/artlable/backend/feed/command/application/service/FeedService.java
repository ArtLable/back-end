package com.artlable.backend.feed.command.application.service;

import com.artlable.backend.feed.command.application.dto.create.FeedCreateRequestDTO;
import com.artlable.backend.feed.command.application.dto.read.FeedReadResponseDTO;
import com.artlable.backend.feed.command.application.dto.update.FeedUpdateRequestDTO;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.feed.command.domain.repository.FeedRepository;
import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    //전체 피드 조회
    @Transactional(readOnly = true)
    public List<FeedReadResponseDTO> findAllFeeds() {

        //조회
        List<Feed> feeds = feedRepository.findByFeedIsDeletedFalseOrderByFeedNoDesc();
        //DTO 리스트 생성
        List<FeedReadResponseDTO> feedlist = new ArrayList<>();
        // 조회결과 삽입
        for (Feed feed : feeds) {
            FeedReadResponseDTO feedReadResponseDTO = new FeedReadResponseDTO(feed);
            feedlist.add(feedReadResponseDTO);
        }

        return feedlist;
    }

    // 특정피드 조회
    @Transactional(readOnly = true)
    public FeedReadResponseDTO findFeed(Long feedNo) {

        //조회
        Feed feed = feedRepository.findByFeedNo(feedNo);
        //DTO생성
        FeedReadResponseDTO responseDTO = new FeedReadResponseDTO(feed);


        return responseDTO;
    }

    //글 작성
    @Transactional
    public Long createFeed(FeedCreateRequestDTO requestDTO, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // FeedCreateRequestDTO를 사용하여 Feed 엔터티를 생성합니다.
        Feed feed = requestDTO.toEntity();
        feed.setMember(member); // 인증된 사용자의 정보를 Feed 엔터티의 member 필드에 설정합니다.

        // Feed 엔터티를 저장합니다.
        feedRepository.save(feed);

        // 생성된 Feed의 ID를 반환합니다.
        return feed.getFeedNo();
    }

    //피드 수정
    @Transactional
    public Long updateFeed(Long feedNo, FeedUpdateRequestDTO requestDTO, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 피드 번호로 피드를 조회합니다.
        Feed feed = feedRepository.findByFeedNo(feedNo);

        // 인증된 사용자가 피드의 소유자인지 확인합니다.
        if (!feed.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 피드를 수정할 권한이 없습니다.");
        }

        // 피드 내용을 업데이트합니다.
        feed.setFeedContent(requestDTO.getFeedContent());

        return feed.getFeedNo();
    }

    //피드 삭제
    @Transactional
    public Long deleteFeed(Long feedNo, String accessToken ) {

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 피드 번호로 피드를 조회합니다.
        Feed feed = feedRepository.findByFeedNo(feedNo);

        // 인증된 사용자가 피드의 소유자인지 확인합니다.
        if (!feed.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 피드를 수정할 권한이 없습니다.");
        }

//        feed.setFeedIsDeleted(true);

        return feed.getFeedNo();
    }

}
