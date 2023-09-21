package com.artlable.backend.file.command.application.controller;

import com.artlable.backend.common.response.ApiResponse;
import com.artlable.backend.file.command.application.dto.SingleFileRequest;
import com.artlable.backend.file.command.application.service.FileService;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class FileController {

    private final FileService fileService;
    private Long fileNo;

    @ApiOperation(value = "파일 업로드")
    @PostMapping("/files")
    public ResponseEntity<ApiResponse> uploadSingleFile(@RequestParam MultipartFile webToonSingleFile,
                                                        @RequestParam MultipartFile nobleSingleFile,
                                                        SingleFileRequest singleFileDTO) throws IOException {
        singleFileDTO.setWebToonSingleFile(webToonSingleFile);
        singleFileDTO.setNobleSingleFile(nobleSingleFile);

        if (webToonSingleFile.isEmpty() || nobleSingleFile.isEmpty()) {
            throw new IllegalArgumentException("사진을 첨부해주세요");
        }

        if (!webToonSingleFile.getContentType().startsWith("image") ||
                !nobleSingleFile.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("이미지 형식의 파일을 올려주세요");
        }

        return ResponseEntity.ok(ApiResponse.success("업로드 성공",
                fileService.createAISingleFile(fileNo, singleFileDTO)));
    }


//    @Autowired
//    public FileController(FileService fileService) {
//        this.fileService = fileService;
//    }
//
//    @ApiOperation(value = "파일 업로드")
//    @PostMapping("/files")
//    public void uploadFile(@RequestParam("files") MultipartFile[] fileList) throws IOException {
//        fileService.uploadFile(fileList);
//    }


//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files) {
//        try {
//            fileService.uploadFile(files);

//            return ResponseEntity.status(HttpStatus.OK).body("파일 업로드 성공");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("파일 업로드 실패: " + e.getMessage());
//        }
//    }
//    @ApiOperation(value = "파일 다운로드")
//    @GetMapping("/files/{fileNo}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileNo) {
//        try {
//            byte[] fileContent = fileService.downloadFile(fileNo);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 바이너리 데이터를 나타내는 미디어 타입 = 다운로드 파일의 컨텐츠 타입으로 사용
//            headers.setContentDispositionFormData("첨부파일", "파일 이름");
//
//            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            // 파일 다운로드 실패 시 예외 처리
//            e.printStackTrace();
//            throw new RuntimeException("파일 다운로드 실패: " + e.getMessage());
//        }
//    }


