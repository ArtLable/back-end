package com.artlable.backend.files.command.application.service;

import com.artlable.backend.common.MD5Generator;
import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import com.artlable.backend.files.command.domain.repository.FilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FilesRepository fileRepository;

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
    //    @Transactional(readOnly = true)
//    public List<MultiFilesReadDTO> getFiles(Long boardNo) {
//        List<MultiFiles> multiFilesList = multiFilesRepository.findByFreeBoardPost_BoardNo(boardNo);
//        List<MultiFilesReadDTO> multiFilesReadDTOList = new ArrayList<>();
//
//        for (MultiFiles multiFiles : multiFilesList) {
//            MultiFilesReadDTO multiFilesReadDTO = new MultiFilesReadDTO();
//            multiFilesReadDTO.setFileNo(multiFiles.getFileNo());
//            multiFilesReadDTO.setOriginFileName(multiFiles.getOriginFileName());
//            multiFilesReadDTO.setFileName(multiFiles.getFileName());
//            multiFilesReadDTO.setFilePath(multiFiles.getFilePath());
//
//            multiFilesReadDTOList.add(multiFilesReadDTO);
//        }
//
//        return multiFilesReadDTOList;
//    }
}

