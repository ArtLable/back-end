package com.artlable.backend.files.command.application.service;

import com.artlable.backend.common.MD5Generator;
import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import com.artlable.backend.files.command.domain.aggregate.entity.Files;
import com.artlable.backend.files.command.domain.repository.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FilesRepository fileRepository;

    //파일 업로드
    @Transactional
    public List<FileRequestDTO> saveFiles(List<MultipartFile> files) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        //DTO 반환객체
        List<FileRequestDTO> multiFilesWriteDTOList = new ArrayList<>();
        //저장위치변수
        String uploadRoot = Paths.get(System.getProperty("user.home"))
                .resolve("upload").toString();
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

    //파일 다운로드
    @Transactional(readOnly = true)
    public Map<String, Object> downloadFile(Long fileNo) throws Exception {
        // DB에서 파일 정보 조회
        Files files = fileRepository.findById(fileNo)
                .orElseThrow(() -> new FileNotFoundException("파일을 조회할수 없습니다. " + fileNo));

        try {
            // 파일 경로 생성
            String uploadRoot = Paths.get(System.getProperty("user.home"))
                    .resolve("upload").toString();
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
                .resolve("upload").toString();
        Path filePath = Paths.get(uploadRoot).resolve(files.getFileName()).normalize();

        // 이미지 파일의 URL을 반환
        return filePath.toString();
    }
}

