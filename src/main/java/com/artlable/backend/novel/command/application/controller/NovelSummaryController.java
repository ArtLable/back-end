package com.artlable.backend.novel.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.files.command.application.dto.novel.CreateNovelSummaryFileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelCreateSummaryDTO;

import com.artlable.backend.novel.command.application.dto.novelsummary.NovelReadSummaryDTO;
import com.artlable.backend.novel.command.application.dto.novelsummary.NovelUpdateSummaryDTO;
import com.artlable.backend.novel.command.domain.service.NovelSummaryService;
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

@Api(tags= "NOVEL SUMMARY API")
@RestController
@RequestMapping("/api/v1/novels")
@RequiredArgsConstructor
public class NovelSummaryController {

    private final NovelSummaryService novelSummaryService;
    private final FileService fileService;

    @ApiOperation(value = "전체 요약 조회")
    @GetMapping("/summaries")
    public ResponseEntity<?> findAllNovelSummaries() {

        try {
            Map<String, Object> responseMap = new HashMap<>();
            List<NovelReadSummaryDTO> summaries = novelSummaryService.findNovelAllSummaries();
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
            NovelReadSummaryDTO readSummary = novelSummaryService.findSummary(summaryNo);
            responseMap.put("summaries", readSummary);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일 캐릭터 조회 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "요약 생성")
    @PostMapping(value = "/summaries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> createSummary(
            @RequestBody NovelCreateSummaryDTO requestDTO,
            @RequestHeader("Authorization") String accessToken) {

        try {
            Long summaryNo = novelSummaryService.createSummary(requestDTO, accessToken);
            List<CreateNovelSummaryFileRequestDTO> fileRequestDTO = fileService.NovelSummarySaveFile(requestDTO.getFiles(), summaryNo, accessToken);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("summaryNo",summaryNo);
            responseMap.put("uploadedFiles", fileRequestDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "요약 생성 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "요약 수정")
    @PutMapping("/summaries/{summaryNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long summaryNo,
                                        @RequestBody NovelUpdateSummaryDTO updateSummary,
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
