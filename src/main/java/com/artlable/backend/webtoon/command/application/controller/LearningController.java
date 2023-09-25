package com.artlable.backend.webtoon.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.files.command.application.dto.novel.CreateNovelSummaryFileRequestDTO;
import com.artlable.backend.files.command.application.dto.webtoon.CreateWebtoonLerningFileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningCreateDTO;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningReadDTO;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningUpdateDTO;
import com.artlable.backend.webtoon.command.application.service.LearningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "WEBTOON LEARNING API")
@RestController
@RequestMapping("/api/v1/webtoons")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;
    private final FileService fileService;


    @ApiOperation(value = "전체 학습 조회")
    @GetMapping("/learnings")
    public ResponseEntity<ResponseMessage> findAllLearning() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<LearningReadDTO> learnings = learningService.findAllLearnings();
            responseMap.put("learnings", learnings);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 학습 조회 성공!", responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "학습 번호로 조회")
    @GetMapping("/learnings/{learningNo}")
    public ResponseEntity<ResponseMessage> findLearningById(@PathVariable Long learningNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            LearningReadDTO learningRead = learningService.findLearning(learningNo);
            responseMap.put("learnings", learningRead);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 학습 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "학습 작성")
    @PostMapping(value = "/learnings", consumes = "multipart/form-data")
    public ResponseEntity<ResponseMessage> createNovel(
            @RequestBody LearningCreateDTO requestDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestHeader("Authorization") String accessToken) {

        try {
            Long learningNo = learningService.createLearning(requestDTO, accessToken);
            List<CreateWebtoonLerningFileRequestDTO> fileRequestDTO = fileService.WebtoonLearningSaveFile(files, learningNo, accessToken);

            learningService.callExternalServiceAndSaveResult(learningNo, files, requestDTO.getCname(), requestDTO.getSearchText(),accessToken);

            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("learningNo",learningNo);
            responseMap.put("fileNo",fileRequestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "학습 생성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "학습 수정")
    @PutMapping("/learnings/{learningNo}")
    public ResponseEntity<ResponseMessage> modifyInfo(@PathVariable Long learningNo,
                                        @RequestBody LearningUpdateDTO learningUpdate,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultLearningNo = learningService.updateLearning(learningNo, learningUpdate, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("learningNo", resultLearningNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "학습 수정 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "학습 삭제")
    @DeleteMapping("/learnings/{learningNo}")
    public ResponseEntity<?> removeNovel(@PathVariable Long learningNo,
                                         @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultLearningNo = learningService.deleteLearning(learningNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("learningNo", resultLearningNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "학습 삭제 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}
