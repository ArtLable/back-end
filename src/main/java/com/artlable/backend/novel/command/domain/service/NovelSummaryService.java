package com.artlable.backend.novel.command.domain.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelCreateSummary;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelReadSummary;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelUpdateSummary;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import com.artlable.backend.novel.command.domain.repository.NovelSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelSummaryService {

    private final NovelSummaryRepository novelSummaryRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    // 전체 요약 조회
    @Transactional(readOnly = true)
    public List<NovelReadSummary> findNovelAllSummaries() {

        List<NovelSummary> summaries = novelSummaryRepository.findBySummaryIsDeletedFalseOrderBySummaryNoDesc();
        List<NovelReadSummary> summaryList = new ArrayList<>();

        for (NovelSummary novelSummary : summaries) {
            NovelReadSummary novelReadSummary = new NovelReadSummary(novelSummary);
            summaryList.add(novelReadSummary);
        }

        return summaryList;
    }

    // 특정 요약 조회
    @Transactional(readOnly = true)
    public NovelReadSummary findSummary(Long summaryNo) {

        NovelSummary novelSummary = novelSummaryRepository.findBySummaryNo(summaryNo);
        NovelReadSummary readSummary = new NovelReadSummary(novelSummary);

        return readSummary;
    }

    // 요약 생성
    @Transactional
    public Long createSummary(NovelCreateSummary novelCreateSummary, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        NovelSummary novelSummary = novelCreateSummary.toEntity();
        novelSummary.setMember(member);

        novelSummaryRepository.save(novelSummary);

        return novelSummary.getSummaryNo();
    }

    // 요약 수정
    @Transactional
    public Long updateSummary(Long summaryNo, NovelUpdateSummary updateSummary, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        NovelSummary novelSummary = novelSummaryRepository.findBySummaryNo(summaryNo);

        if (!novelSummary.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 요약을 수정할 권한이 없습니다.");
        }

        novelSummary.setSummaryContent(updateSummary.getSummaryContent());
        novelSummary.setSummaryResult(updateSummary.getSummaryResult());

        return novelSummary.getSummaryNo();
    }

    // 요약 삭제
    @Transactional
    public Long deleteSummary(Long summaryNo, String accessToken) {

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 요약 번호로 요약을 조회합니다.
        NovelSummary novelSummary = novelSummaryRepository.findBySummaryNo(summaryNo);

        // 인증된 사용자가 요약의 소유자인지 확인합니다.
        if (!novelSummary.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 요약을 삭제할 권한이 없습니다.");
        }

        return novelSummary.getSummaryNo();
    }
}
