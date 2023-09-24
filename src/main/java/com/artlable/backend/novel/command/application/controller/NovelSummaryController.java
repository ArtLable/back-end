package com.artlable.backend.novel.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelCreateSummary;

import com.artlable.backend.novel.command.application.dto.novelsummary.NovelReadSummary;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelUpdateSummary;
import com.artlable.backend.novel.command.domain.service.NovelSummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags= "SUMMARY API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NovelSummaryController {

    private final NovelSummaryService novelSummaryService;

    @ApiOperation(value = "전체 요약 조회")
    @GetMapping("/summaries")
    public ResponseEntity<?> findAllNovelSummaries() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<NovelReadSummary> summaries = novelSummaryService.findNovelAllSummaries();
            responseMap.put("summaries", summaries);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체 요약 조회 성공!", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "요약 번호로 조회")
    @GetMapping("/summaries/{summaryNo}")
    public ResponseEntity<?> findSummariesById(@PathVariable Long summaryNo) {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            NovelReadSummary readSummary = novelSummaryService.findSummary(summaryNo);
            responseMap.put("summaries", readSummary);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 캐릭터 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "요약 생성")
    @PostMapping("/summaries")
    public ResponseEntity<?> createSummary(@RequestBody NovelCreateSummary createSummary,
                                             @RequestHeader("Authorization") String accessToken) {

        try {
            Long summaryNo = novelSummaryService.createSummary(createSummary, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("summaryNo",summaryNo);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "요약 생성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "요약 수정")
    @PutMapping("/summaries/{summaryNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long summaryNo,
                                        @RequestBody NovelUpdateSummary updateSummary,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultSummaryNo = novelSummaryService.updateSummary(summaryNo, updateSummary, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("summaryNo", resultSummaryNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "요약 수정 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @ApiOperation(value = "요약 삭제")
    @DeleteMapping("/summaries/{summaryNo}")
    public ResponseEntity<?> removeSummary(@PathVariable Long summaryNo,
                                             @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultSummaryNo = novelSummaryService.deleteSummary(summaryNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("characterNo", resultSummaryNo);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "요약 삭제 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}