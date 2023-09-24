package com.artlable.backend.webtoon.command.application.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceCreate;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceRead;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceUpdate;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Inference;
import com.artlable.backend.webtoon.command.domain.repository.InferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InferenceService {

    private final InferenceRepository inferenceRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    // 전제 추론 조회
    @Transactional(readOnly = true)
    public List<InferenceRead> findAllInfereces() {

        List<Inference> inferences = inferenceRepository.findByInferenceIsDeletedFalseOrderByInferenceNoDesc();
        List<InferenceRead> inferenceList = new ArrayList<>();

        for (Inference inference : inferences) {
            InferenceRead inferenceRead = new InferenceRead(inference);
            inferenceList.add(inferenceRead);
        }

        return inferenceList;
    }

    // 특정 추론 조회
    @Transactional(readOnly = true)
    public InferenceRead findInference(Long inferenceNo) {

        Inference inference = inferenceRepository.findByInferenceNo(inferenceNo);
        InferenceRead inferenceRead = new InferenceRead(inference);

        return inferenceRead;
    }

    // 추론 생성
    @Transactional
    public Long createInference(InferenceCreate inferenceCreate, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Inference inference = inferenceCreate.toEntity();
        inference.setMember(member);

        inferenceRepository.save(inference);

        return inference.getInferenceNo();
    }

    // 추론 수정
    @Transactional
    public Long updateInference(Long inferenceNo, InferenceUpdate inferenceUpdate, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Inference inference = inferenceRepository.findByInferenceNo(inferenceNo);

        if (!inference.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 추론을 수정할 권한이 없습니다.");
        }

        inference.setInferenceContent(inferenceUpdate.getInferenceContent());
        inference.setInferenceResult(inferenceUpdate.getInferenceResult());

        return inference.getInferenceNo();
    }

    // 추론 삭제
    @Transactional
    public Long deleteInference(Long inferenceNo, String accessToken) {

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member authenticatedMember = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 요청된 추론 번호로 소설을 조회합니다.
        Inference inference = inferenceRepository.findByInferenceNo(inferenceNo);

        // 인증된 사용자가 추론의 소유자인지 확인합니다.
        if (!inference.getMember().getMemberEmail().equals(authenticatedMember.getMemberEmail())) {
            throw new IllegalArgumentException("해당 추론을 삭제할 권한이 없습니다.");
        }

        return inference.getInferenceNo();
    }
}
