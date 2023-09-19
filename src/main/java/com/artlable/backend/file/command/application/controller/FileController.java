//package com.artlable.backend.file.command.application.controller;
//
//import com.artlable.backend.file.command.application.service.FileService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/v1")
//public class FileController {
//
//    private final FileService fileService;
//
//    @Autowired
//    public FileController(FileService fileService) {
//        this.fileService = fileService;
//    }
//
//    @ApiOperation(value = "파일 업로드")
//    @PostMapping("/files")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            fileService.uploadFile(file);
//            return ResponseEntity.status(HttpStatus.OK).body("파일 업로드 성공");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("파일 업로드 실패: " + e.getMessage());
//        }
//    }
//
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
//}