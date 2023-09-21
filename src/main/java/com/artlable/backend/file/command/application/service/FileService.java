package com.artlable.backend.file.command.application.service;

import com.artlable.backend.file.command.application.dto.FileResponse;
import com.artlable.backend.file.command.application.dto.SingleFileRequest;
import com.artlable.backend.file.command.domain.aggregate.vo.MemberVo;
import com.artlable.backend.file.command.domain.repository.FileRepository;
import com.artlable.backend.file.command.domain.service.FileDomainService;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.nimbusds.jose.util.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileDomainService fileDomainService;
    private final FileRepository fileRepository;

    @Transactional
    public FileResponse createAISingleFile(Long fileNo, SingleFileRequest singleFileDTO) throws IOException {

        FileResponse aiFileDTO = fileDomainService.getAISingleFile(singleFileDTO);

//        String filePath = "/files";

//        File dir = new File(filePath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        String originalFileName = aiFileDTO.getAiFile().getOriginalFilename();  //원본 파일 이름
//        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);  //파일 확장자
//        String saveName = UUID.randomUUID().toString().replace("-", "") + "." + ext;  //저장되는 이름
//
//        try {
//            aiFileDTO.getAiFile().transferTo(new File(filePath + saveName));
//
//            com.artlable.backend.file.command.domain.aggregate.entity.File file =
//                    com.artlable.backend.file.command.domain.aggregate.entity.File.builder()
//                            .originalFileName(originalFileName)
////                            .saveFileName(saveName)
//                            .filePath(filePath)
//                            .memberVo(new MemberVo(fileNo))
//                            .build();
//
//            fileRepository.save(file);
//        } catch (IOException e) {
//            new File(filePath + saveName).delete();  // 업로드 후 디비 저장 중 오류났을 때 업로드된 이미지 삭제
//        }

        return aiFileDTO;
    }

//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
//
//        for (MultipartFile file : fileList) {
//            formData.add("file", new FileSystemResource("C:\\Users\\user\\Desktop\\files\\빵빵이.jpg"));
//        }
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                "http://192.168.0.63:8080/files",
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
//        String response = responseEntity.getBody();
//    }
//            try {
//                byte[] fileBytes = file.getBytes();
//                ByteArrayResource resources = new ByteArrayResource(fileBytes);
//
//                formData.add("files", new HttpEntity<>(resources));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("업로드할 파일이 비어 있습니다.");
//        }
//
//        String originalFileName = file.getOriginalFilename();
//        String randomDirectoryName = UUID.randomUUID().toString(); // 랜덤 디렉토리 이름 생성
//        String filePath = fileDir + "C:\\Users\\user\\Desktop\\files" + randomDirectoryName + "C:\\Users\\user\\Desktop\\files" + originalFileName; // 파일 경로 설정
//
//        // 파일 정보 설정
//        File savedFile = new File(filePath);
//        savedFile.setFileName(originalFileName);
//        savedFile.setFileType(file.getContentType());
//        savedFile.setFileSize(file.getSize());
//        savedFile.setFilePath(filePath);
//
//        // 랜덤 디렉토리 생성
//        Path directoryPath = FileSystems.getDefault().getPath(fileDir, randomDirectoryName);
//        Files.createDirectories(directoryPath);
//
//        // 파일 저장
//        // Files.copy = 업로드한 파일의 입력 스트림을 서버 파일 시스템에 복사
//        try {
//            Files.copy(file.getInputStream(), FileSystems.getDefault().getPath(filePath), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new IllegalArgumentException("파일 저장 실패: " + e.getMessage());
//        }
//
//        // 파일 정보 저장
//        fileRepository.save(savedFile);
//    }

//    @Transactional
//    public byte[] downloadFile(Long fileNo) throws IOException {
//        File file = fileRepository.findById(fileNo).orElse(null);
//
//        if (file != null) {
//            Path filePath = FileSystems.getDefault().getPath(file.getFilePath()); // 파일 경로를 'Path' 객체로 변환 -> 파일 시스템에서 파일을 읽을 수 있는 경로가 생성
//            return Files.readAllBytes(filePath);
//        } else {
//            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
//        }
//    }
}
