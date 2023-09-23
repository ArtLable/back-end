package com.artlable.backend.files.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.files.command.application.dto.FileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
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

    @ApiOperation(value = "파일 다운로드")
    @GetMapping("/files/{fileNo}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileNo) {
        try {
            // 파일 리소스 로드
            Map<String, Object> responseMap = fileService.downloadFile(fileNo);
            Resource resource = (Resource) responseMap.get("resource");
            String originalFileName = (String) responseMap.get("originalFileName");

            // 파일 다운로드를 위한 헤더 설정
            String contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
                    .body(resource);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을수없습니다.", ex);
        }
    }

    @ApiOperation(value = "이미지 주소 조회")
    @GetMapping("/files/image/{fileNo}")
    public ResponseEntity<ResponseMessage> getImageUrl(@PathVariable Long fileNo) {
        try {
            String imageUrl = fileService.getImageUrl(fileNo);
            ResponseMessage responseMessage = new ResponseMessage(
                    HttpStatus.OK.value(),
                    "이미지 URL이 성공적으로 조회되었습니다.",
                    Map.of("imageUrl", imageUrl)
            );
            return ResponseEntity.ok().body(responseMessage);
        } catch (FileNotFoundException ex) {
            ResponseMessage errorResponse = new ResponseMessage(HttpStatus.NOT_FOUND.value(), "이미지를 찾을 수 없습니다.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}