package com.artlable.backend.novel.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.files.command.application.dto.CreateFeedFileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
import com.artlable.backend.novel.command.application.dto.novel.NovelCreate;
import com.artlable.backend.novel.command.application.dto.novel.NovelRead;

import com.artlable.backend.novel.command.application.dto.novel.NovelUpdate;
import com.artlable.backend.novel.command.domain.service.NovelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "NOVEL API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NovelController {

    private final NovelService novelService;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    @ApiOperation(value = "전체 소설 조회")
    @GetMapping("/novels")
    public ResponseEntity<ResponseMessage> findAllNovels() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<NovelRead> novels = novelService.findAllNovels();
            responseMap.put("novels", novels);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 소설 조회 성공!", responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "소설 번호로 조회")
    @GetMapping("/novels/{novelNo}")
    public ResponseEntity<ResponseMessage> findNovelById(@PathVariable Long novelNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            NovelRead novelRead = novelService.findNovel(novelNo);
            responseMap.put("novels", novelRead);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 소설 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "소설 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "novel", value = "Novel JSON", readOnly = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "files", value = "Files", dataType = "file", paramType = "form", allowMultiple = true)
    })
    @PostMapping(value = "/novels", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> createNovel(
            @RequestPart(value = "novel") String novelJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestHeader("Authorization") String accessToken) {

        try {
            NovelCreate novelCreate = objectMapper.readValue(novelJson, NovelCreate.class);
            Long novelNo = novelService.createNovel(novelCreate, accessToken);
            List<CreateFeedFileRequestDTO> uploadedFiles = fileService.feedSaveFile(files, novelNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("novelNo",novelNo);
            responseMap.put("uploadedFiles", uploadedFiles);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "소설 작성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "소설 수정")
    @PutMapping("/novels/{novelNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long novelNo,
                                        @RequestBody NovelUpdate novelUpdate,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultNovelNo = novelService.updateNovel(novelNo, novelUpdate, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("novelNo", resultNovelNo);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "소설 수정 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "소설 삭제")
    @DeleteMapping("/novels/{novelNo}")
    public ResponseEntity<?> removeNovel(@PathVariable Long novelNo,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultNovelNo = novelService.deleteNovel(novelNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("novelNo", resultNovelNo);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "소설 삭제 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}
