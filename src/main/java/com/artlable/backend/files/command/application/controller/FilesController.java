package com.artlable.backend.files.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags= "File API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FilesController {

    private final FileService fileService;

    @ApiOperation(value = "파일 업로드")
    @PostMapping(value = "/files", consumes = "multipart/form-data")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestPart("file") List<MultipartFile> fileList) {

        if (fileList.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "업로드할 파일이 없습니다.", null));
        }

        List<FileRequestDTO> uploadedFiles;
        try {
            uploadedFiles = fileService.saveFiles(fileList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "파일 업로드 중 오류가 발생했습니다: " + e.getMessage(), null));
        }

        Map<String, Object> results = Map.of("uploadedFiles", uploadedFiles);
        return ResponseEntity.ok(new ResponseMessage(HttpStatus.OK.value(), "파일이 성공적으로 업로드되었습니다.", results));
    }
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


