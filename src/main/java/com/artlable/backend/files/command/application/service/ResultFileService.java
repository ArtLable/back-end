package com.artlable.backend.files.command.application.service;

import com.artlable.backend.common.MD5Generator;
import com.artlable.backend.files.command.application.dto.webtoon.CreateWebtoonLerningFileRequestDTO;
import com.artlable.backend.files.command.domain.repository.FilesRepository;
import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelCreateCharacterDTO;
import com.artlable.backend.novel.command.application.dto.novelcharacter.NovelResultCharacterDTO;
import com.artlable.backend.novel.command.domain.repository.NovelCharacterRepository;
import com.artlable.backend.novel.command.infra.service.NovelCharacterAiService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultFileService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final FilesRepository filesRepository;
    private final NovelCharacterAiService novelCharacterAiService;


    //캐릭터생성 결과
    @Transactional
    public List<NovelResultCharacterDTO> saveImage(NovelCreateCharacterDTO createDto, String accessToken, Long relatedEntityNo) throws IOException, NoSuchAlgorithmException {
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // NovelCharacterAiService 호출하여 이미지 바이트 데이터 받아오기
        byte[] imageBytes = novelCharacterAiService.callExternalApiAndSaveResult(createDto);
        if (imageBytes == null) {
            throw new IllegalArgumentException("이미지를 받아오지 못했습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        String uploadRoot = Paths.get(System.getProperty("user.home"))
                .resolve("toon-maker")
                .resolve("toon-maker-frontend")
                .resolve("public")
                .resolve("upload")
                .toString();

        File directory = new File(uploadRoot);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalArgumentException("파일 디렉토리 생성에 실패했습니다.");
        }

        String originFileName = "result.jpg";
        String fileName = new MD5Generator(originFileName).toString();
        String filePath = uploadRoot + File.separator + fileName + ".jpg";

        Files.write(Paths.get(filePath), imageBytes);

        NovelResultCharacterDTO characterDTO = NovelResultCharacterDTO.builder()
                .originFileName(originFileName)
                .fileName(fileName)
                .filePath(filePath)
                .memberNo(member.getMemberNo())
                .resultNo(relatedEntityNo)
                .build();

        filesRepository.save(characterDTO.toEntity());

        return List.of(characterDTO);
    }

    //웹툰 학습
    public List<CreateWebtoonLerningFileRequestDTO> WebtoonLearningSaveFile(List<MultipartFile> files, Long inputLearningNo, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        if (!(files==null||!files.isEmpty())) {
            throw new IllegalArgumentException("파일을 첨부해 주세요.");
        }

        //DTO 반환객체
        List<CreateWebtoonLerningFileRequestDTO> requestDTO = new ArrayList<>();

        //저장위치변수
        String uploadRoot = Paths.get(System.getProperty("user.home"))
                .resolve("toon-maker")
                .resolve("toon-maker-frontend")
                .resolve("public")
                .resolve("upload")
                .toString();

        System.out.println(uploadRoot);
        //디렉토리생성
        File directory = new File(uploadRoot);

        //디렉토리 없을경우 오류처리
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("디렉토리가 성공적으로 생성되었습니다.");
            } else {
                System.out.println("디렉토리 생성에 실패했습니다.");
                throw new IllegalArgumentException("파일 디렉토리 생성에 실패했습니다.");
            }
        }
        for (MultipartFile file : files) { // 반복문
            if (!file.isEmpty()) {

                String originFileName = file.getOriginalFilename();
                if (originFileName == null) {
                    // 파일 이름이 null인 경우에 대한 예외 처리
                    throw new IllegalArgumentException("파일 이름이 null입니다.");
                }

                String[] fileType = originFileName.split("[.]");


                String fileName = new MD5Generator(originFileName).toString();
                String filePath = uploadRoot + File.separator + fileName+"."+fileType[1];

                try {
                    file.transferTo(new File(filePath));
                } catch (IOException e) {
                    // 파일 전송 과정에서 발생한 예외 처리
                    throw new IllegalArgumentException("파일 전송 중 오류가 발생했습니다.");
                }

                CreateWebtoonLerningFileRequestDTO createNovelSummaryFileRequestDTO = CreateWebtoonLerningFileRequestDTO.builder()
                        .originFileName(originFileName)
                        .fileName(fileName+"."+fileType[1])
                        .filePath(filePath)
                        .inputLearningNo(inputLearningNo)
                        .memberNo(member.getMemberNo())
                        .build();
                filesRepository.save(createNovelSummaryFileRequestDTO.toEntity());

                requestDTO.add(createNovelSummaryFileRequestDTO);
            }
        }
        return requestDTO;
    }
}
