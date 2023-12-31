package com.artlable.backend.novel.command.domain.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.novel.command.application.dto.novel.NovelCreateDTO;
import com.artlable.backend.novel.command.application.dto.novel.NovelReadDTO;
import com.artlable.backend.novel.command.application.dto.novel.NovelUpdateDTO;
import com.artlable.backend.novel.command.domain.aggregate.entity.Novel;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelCharacter;
import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import com.artlable.backend.novel.command.domain.repository.NovelCharacterRepository;
import com.artlable.backend.novel.command.domain.repository.NovelRepository;
import com.artlable.backend.novel.command.domain.repository.NovelSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;
    private final NovelCharacterRepository novelCharacterRepository;
    private final NovelSummaryRepository novelSummaryRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    // 전제 소설 조회
    @Transactional(readOnly = true)
    public List<NovelReadDTO> findAllNovels() {

        List<Novel> novels = novelRepository.findAll();
        List<NovelReadDTO> novelList = new ArrayList<>();

        for (Novel novel : novels) {
            NovelReadDTO novelRead = new NovelReadDTO(novel);
            novelList.add(novelRead);
        }

        return novelList;
    }

    // 특정 소설 조회
    @Transactional(readOnly = true)
    public NovelReadDTO findNovel(Long novelNo) {

        Novel novel = novelRepository.findByNovelNo(novelNo);
        NovelReadDTO novelRead = new NovelReadDTO(novel);

        return novelRead;
    }

    // 소설 생성
    @Transactional
    public Long createNovel(NovelCreateDTO novelCreate, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Novel novel = novelCreate.toEntity();
        novel.setMember(member);

        novelRepository.save(novel);

        return novel.getNovelNo();
    }

    // 소설 수정
    @Transactional
    public Long updateNovel(Long novelNo, NovelUpdateDTO novelUpdate, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Novel novel = novelRepository.findByNovelNo(novelNo);

        if (!novel.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 소설을 수정할 권한이 없습니다.");
        }

        novel.setNovelContent(novelUpdate.getNovelContent());
        novel.setNovelTitle(novelUpdate.getNovelTitle());
        novel.setNovelGenre(novelUpdate.getNovelGenre());

        return novel.getNovelNo();
    }

    // 소설 삭제
    @Transactional
    public Long deleteNovel(Long novelNo, String accessToken) {

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 소설 번호로 소설을 조회합니다.
        Novel novel = novelRepository.findByNovelNo(novelNo);

        // 인증된 사용자가 소설의 소유자인지 확인합니다.
        if (!novel.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 소설을 삭제할 권한이 없습니다.");
        }

        return novel.getNovelNo();
    }

    @Transactional
    public void mapEntities(Long novelNo, Long characterNo, Long summaryNo, String accessToken) {

        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        Novel novel = novelRepository.findById(novelNo)
                .orElseThrow(() -> new IllegalArgumentException("Novel not found: " + novelNo));

        NovelCharacter character = novelCharacterRepository.findById(characterNo)
                .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterNo));

        NovelSummary summary = novelSummaryRepository.findById(summaryNo)
                .orElseThrow(() -> new IllegalArgumentException("Summary not found: " + summaryNo));

        novel.addCharacter(character);
        novel.addSummary(summary);
    }
}