package com.artlable.backend.webtoon.command.application.service;

import com.artlable.backend.files.command.application.dto.webtoon.CreateWebtoonLerningFileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.files.command.domain.repository.FilesRepository;
import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningCreateDTO;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningReadDTO;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningUpdateDTO;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import com.artlable.backend.webtoon.command.domain.repository.LearningRepository;
import com.artlable.backend.webtoon.command.infra.service.BASE64DecodedMultipartFile;
import com.artlable.backend.webtoon.command.infra.service.LearningWebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final LearningRepository learningRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final FilesRepository fileRepository;
    private final LearningWebtoonService learningWebtoonService;
    private final TokenProvider tokenProvider;

    // 전제 학습 조회
    @Transactional(readOnly = true)
    public List<LearningReadDTO> findAllLearnings() {

        List<Learning> learnings = learningRepository.findByLearningIsDeletedFalseOrderByLearningNoDesc();
        List<LearningReadDTO> learningList = new ArrayList<>();

        for (Learning learning : learnings) {
            LearningReadDTO learningRead = new LearningReadDTO(learning);
            learningList.add(learningRead);
        }

        return learningList;
    }

    // 특정 학습 조회
    @Transactional(readOnly = true)
    public LearningReadDTO findLearning(Long learningNo) {

        Learning learning = learningRepository.findByLearningNo(learningNo);
        LearningReadDTO learningRead = new LearningReadDTO(learning);

        return learningRead;
    }

    // 학습 생성
    @Transactional
    public Long createLearning(LearningCreateDTO createDTO, String accessToken) {

        // 토큰의 유효성 검사
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Learning learning = createDTO.toEntity();
        learning.setMember(member);

        learningRepository.save(learning);

        return learning.getLearningNo();
    }

    //api 호출
    @Transactional
    public void callExternalServiceAndSaveResult(Long learningNo, List<MultipartFile> files, String cname, String search_text, String accessToken) throws IOException, NoSuchAlgorithmException {
        // 외부 서비스 호출
        List<String> resultImages = learningWebtoonService.sendFilesAndRetrieveImages(files, cname, search_text);
        // Learning 엔터티 찾기
        Learning learning = learningRepository.findById(learningNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 Learning을 찾을 수 없습니다."));

        // Base64 인코딩된 문자열을 MultipartFile로 변환하여 저장
        for (String image : resultImages) {
            // Base64 문자열을 byte[]로 변환
            byte[] fileBytes = Base64.getDecoder().decode(image);

            // byte[]를 MultipartFile로 변환
            MultipartFile resultFile = new BASE64DecodedMultipartFile(fileBytes, "result_" + System.currentTimeMillis() + ".jpg");

            // 파일 저장
            List<CreateWebtoonLerningFileRequestDTO> savedFiles = fileService.WebtoonLearningSaveFile(List.of(resultFile), learningNo, accessToken);

            // Learning 엔터티의 resultFiles에 추가
            for (CreateWebtoonLerningFileRequestDTO savedFile : savedFiles) {
                Files fileEntity = fileRepository.findById(savedFile.getFileNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));
                learning.getResultFiles().add(fileEntity);
            }
        }
    }

    // 학습 수정
    @Transactional
    public Long updateLearning(Long learningNo, LearningUpdateDTO learningUpdate, String accessToken) {

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

        learning.setCname(learningUpdate.getCname());
        learning.setSearchText(learningUpdate.getSearchText());

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
