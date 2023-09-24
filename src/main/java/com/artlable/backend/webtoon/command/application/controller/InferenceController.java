package com.artlable.backend.webtoon.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceCreate;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceRead;
import com.artlable.backend.webtoon.command.application.dto.inference.InferenceUpdate;
import com.artlable.backend.webtoon.command.application.service.InferenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "INFERENCE API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InferenceController {

    private final InferenceService inferenceService;

    @ApiOperation(value = "전체 추론 조회")
    @GetMapping("/inferences")
    public ResponseEntity<?> findAllInferences() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<InferenceRead> inferences = inferenceService.findAllInfereces();
            responseMap.put("inferences", inferences);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 추론 조회 성공!", responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "추론 번호로 조회")
    @GetMapping("/inferences/{inferenceNo}")
    public ResponseEntity<?> findInferenceById(@PathVariable Long inferenceNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            InferenceRead inferenceRead = inferenceService.findInference(inferenceNo);
            responseMap.put("inferences", inferenceRead);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 추론 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "추론 작성")
    @PostMapping("/inferences")
    public ResponseEntity<?> createNovel(@RequestBody InferenceCreate inferenceCreate,
                                         @RequestHeader("Authorization") String accessToken) {

        try {
            Long inferenceNo = inferenceService.createInference(inferenceCreate, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("inferenceNo",inferenceNo);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "추론 생성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "추론 수정")
    @PutMapping("/inferences/{inferenceNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long inferenceNo,
                                        @RequestBody InferenceUpdate inferenceUpdate,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultInferenceNo = inferenceService.updateInference(inferenceNo, inferenceUpdate, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("inferenceNo", resultInferenceNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "추론 수정 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "추론 삭제")
    @DeleteMapping("/inferences/{inferenceNo}")
    public ResponseEntity<?> removeNovel(@PathVariable Long inferenceNo,
                                         @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultInferenceNo = inferenceService.deleteInference(inferenceNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("inferenceNo", resultInferenceNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "소설 삭제 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}
