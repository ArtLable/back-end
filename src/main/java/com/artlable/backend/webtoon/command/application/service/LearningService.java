package com.artlable.backend.webtoon.command.application.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceCreate;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceRead;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceUpdate;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningCreate;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningRead;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningUpdate;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Inference;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import com.artlable.backend.webtoon.command.domain.repository.LearningRepository;
import com.artlable.backend.webtoon.command.infra.service.WebtoonAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final LearningRepository learningRepository;
    private final MemberRepository memberRepository;
    private final WebtoonAiService webtoonAiService;
    private final TokenProvider tokenProvider;

    // 전제 학습 조회
    @Transactional(readOnly = true)
    public List<LearningRead> findAllLearnings() {

        List<Learning> learnings = learningRepository.findByLearningIsDeletedFalseOrderByLearningNoDesc();
        List<LearningRead> learningList = new ArrayList<>();

        for (Learning learning : learnings) {
            LearningRead learningRead = new LearningRead(learning);
            learningList.add(learningRead);
        }

        return learningList;
    }

    // 특정 학습 조회
    @Transactional(readOnly = true)
    public LearningRead findLearning(Long learningNo) {

        Learning learning = learningRepository.findByLearningNo(learningNo);
        LearningRead learningRead = new LearningRead(learning);

        return learningRead;
    }

    // 학습 생성
    @Transactional
    public Long createLearning(LearningCreate learningCreate, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Learning learning = learningCreate.toEntity();
        learning.setMember(member);

        learningRepository.save(learning);

        return learning.getLearningNo();
    }

    // 학습 수정
    @Transactional
    public Long updateLearning(Long learningNo, LearningUpdate learningUpdate, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Learning learning = learningRepository.findByLearningNo(learningNo);

        if (!learning.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 학습을 수정할 권한이 없습니다.");
        }

        learning.setLearningContent(learningUpdate.getLearningContent());

        return learning.getLearningNo();
    }

    // 학습 삭제
    @Transactional
    public Long deleteLearning(Long learningNo, String accessToken) {

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 학습 번호로 소설을 조회합니다.
        Learning learning = learningRepository.findByLearningNo(learningNo);

        // 인증된 사용자가 학습의 소유자인지 확인합니다.
        if (!learning.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 학습을 삭제할 권한이 없습니다.");
        }

        return learning.getLearningNo();
    }
}
