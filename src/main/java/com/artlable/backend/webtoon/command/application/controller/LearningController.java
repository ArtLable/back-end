package com.artlable.backend.webtoon.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceCreate;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceRead;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceUpdate;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningCreate;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningRead;
import com.artlable.backend.webtoon.command.application.dto.learning.LearningUpdate;
import com.artlable.backend.webtoon.command.application.service.LearningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "LEARNING API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    @ApiOperation(value = "전체 학습 조회")
    @GetMapping("/learnings")
    public ResponseEntity<?> findAllLearning() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<LearningRead> learnings = learningService.findAllLearnings();
            responseMap.put("learnings", learnings);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 학습 조회 성공!", responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "학습 번호로 조회")
    @GetMapping("/learnings/{learningNo}")
    public ResponseEntity<?> findLearningById(@PathVariable Long learningNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            LearningRead learningRead = learningService.findLearning(learningNo);
            responseMap.put("learnings", learningRead);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 학습 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "학습 작성")
    @PostMapping("/learnings")
    public ResponseEntity<?> createNovel(@RequestBody LearningCreate learningCreate,
                                         @RequestHeader("Authorization") String accessToken) {

        try {
            Long learningNo = learningService.createLearning(learningCreate, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("learningNo",learningNo);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "학습 생성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "학습 수정")
    @PutMapping("/learnings/{learningNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long learningNo,
                                        @RequestBody LearningUpdate learningUpdate,
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
