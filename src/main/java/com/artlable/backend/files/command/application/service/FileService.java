package com.artlable.backend.files.command.application.service;

import com.artlable.backend.common.MD5Generator;
import com.artlable.backend.files.command.application.dto.feed.CreateFeedFileRequestDTO;
import com.artlable.backend.files.command.application.dto.novel.CreateNovelSummaryFileRequestDTO;
import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import com.artlable.backend.files.command.application.dto.webtoon.CreateWebtoonLerningFileRequestDTO;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.files.command.domain.repository.FilesRepository;
import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import com.artlable.backend.webtoon.command.domain.repository.LearningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FilesRepository fileRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final LearningRepository learningRepository;

    //파일 업로드
    @Transactional
    public List<FileRequestDTO> saveFiles(List<MultipartFile> files) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        //DTO 반환객체
        List<FileRequestDTO> multiFilesWriteDTOList = new ArrayList<>();
        //저장위치변수
        String uploadRoot = Paths.get(System.getProperty("user.home"))
                .resolve("toon-maker")
                .resolve("toon-maker-frontend")
                .resolve("img")
                .resolve("upload")
                .toString();

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

                String fileName = new MD5Generator(originFileName).toString();
                String filePath = uploadRoot + File.separator + fileName;

                try {
                    file.transferTo(new File(filePath));
                } catch (IOException e) {
                    // 파일 전송 과정에서 발생한 예외 처리
                    throw new IllegalArgumentException("파일 전송 중 오류가 발생했습니다.");
                }

                FileRequestDTO multiFilesWriteDTO = FileRequestDTO.builder()
                        .originFileName(originFileName)
                        .fileName(fileName)
                        .filePath(filePath)
                        .build();
                fileRepository.save(multiFilesWriteDTO.toEntity());

                multiFilesWriteDTOList.add(multiFilesWriteDTO);
            }
        }
        return multiFilesWriteDTOList;
    }

    @Transactional
    public List<CreateFeedFileRequestDTO> feedSaveFile(List<MultipartFile> files, Long feedNo, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // accessToken을 사용하여 사용자를 인증하고 해당 사용자의 정보를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("파일을 첨부해 주세요.");
        }

        //DTO 반환객체
        List<CreateFeedFileRequestDTO> multiFilesWriteDTOList = new ArrayList<>();

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

                CreateFeedFileRequestDTO multiFilesWriteDTO = CreateFeedFileRequestDTO.builder()
                        .originFileName(originFileName)
                        .fileName(fileName+"."+fileType[1])
                        .filePath(filePath)
                        .feedNo(feedNo)
                        .memberNo(member.getMemberNo())
                        .build();
                fileRepository.save(multiFilesWriteDTO.toEntity());

                multiFilesWriteDTOList.add(multiFilesWriteDTO);
            }
        }
        return multiFilesWriteDTOList;
    }

    //json 파일로 변환 저장
    @Transactional
    public List<CreateWebtoonLerningFileRequestDTO> saveResultImages(Long learningNo, List<String> resultImages, String accessToken) throws IOException, NoSuchAlgorithmException {
        if (!tokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userEmail = authentication.getName();
        Member member = memberRepository.findMemberByMemberEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Learning learning = learningRepository.findById(learningNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 Learning을 찾을 수 없습니다."));

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

        List<CreateWebtoonLerningFileRequestDTO> savedFiles = new ArrayList<>();
        for (int i = 0; i < resultImages.size(); i++) {
            String image = resultImages.get(i);
            byte[] fileBytes = Base64.getDecoder().decode(image);

            String fileName = (i + 1) + ".jpg";
            String filePath = uploadRoot + File.separator + fileName;

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(fileBytes);
            }

            CreateWebtoonLerningFileRequestDTO fileDTO = CreateWebtoonLerningFileRequestDTO.builder()
                    .originFileName(fileName)
                    .fileName(fileName)
                    .filePath(filePath)
                    .inputLearningNo(learningNo)
                    .memberNo(member.getMemberNo())
                    .build();

            fileRepository.save(fileDTO.toEntity());
            savedFiles.add(fileDTO);
        }

        return savedFiles;
    }

    @Transactional
    public List<CreateNovelSummaryFileRequestDTO> NovelSummarySaveFile(List<MultipartFile> files, Long summaryNo, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {

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
        List<CreateNovelSummaryFileRequestDTO> requestDTO = new ArrayList<>();

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

                CreateNovelSummaryFileRequestDTO createNovelSummaryFileRequestDTO = CreateNovelSummaryFileRequestDTO.builder()
                        .originFileName(originFileName)
                        .fileName(fileName+"."+fileType[1])
                        .filePath(filePath)
                        .summaryNo(summaryNo)
                        .memberNo(member.getMemberNo())
                        .build();
                fileRepository.save(createNovelSummaryFileRequestDTO.toEntity());

                requestDTO.add(createNovelSummaryFileRequestDTO);
            }
        }
        return requestDTO;
    }

    //파일 다운로드
    @Transactional(readOnly = true)
    public Map<String, Object> downloadFile(Long fileNo) throws Exception {
        // DB에서 파일 정보 조회
        Files files = fileRepository.findById(fileNo)
                .orElseThrow(() -> new FileNotFoundException("파일을 조회할수 없습니다. " + fileNo));

        try {
            // 파일 경로 생성
            String uploadRoot = Paths.get(System.getProperty("user.home"))
                    .resolve("toon-maker")
                    .resolve("toon-maker-frontend")
                    .resolve("img")
                    .resolve("upload")
                    .toString();
            Path filePath = Paths.get(uploadRoot).resolve(files.getFileName()).normalize();

            // 파일 리소스 생성
            Resource resource = new UrlResource(filePath.toUri());

            // 파일이 존재하는지 확인
            if(!resource.exists()) {
                throw new FileNotFoundException("파일을 찾을수 없습니다." + files.getFileName());
            }

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("resource", resource);
            responseMap.put("originalFileName", files.getOriginFileName());

            return  responseMap;

        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("파일의 경로가 틀립니다. " + files.getFileName());
        }
    }



    //사진 주소 리턴
    public String getImageUrl(Long fileNo) throws FileNotFoundException {
        Files files = fileRepository.findById(fileNo)
                .orElseThrow(() -> new FileNotFoundException("파일을 조회할 수 없습니다. " + fileNo));

        String uploadRoot = Paths.get(System.getProperty("user.home"))
                .resolve("toon-maker")
                .resolve("toon-maker-frontend")
                .resolve("img")
                .resolve("upload")
                .toString();
        Path filePath = Paths.get(uploadRoot).resolve(files.getFileName()).normalize();

        // 이미지 파일의 URL을 반환
        return filePath.toString();
    }


}
