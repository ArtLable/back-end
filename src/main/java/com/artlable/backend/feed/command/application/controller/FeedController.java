package com.artlable.backend.feed.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.feed.command.application.dto.create.FeedCreateRequestDTO;
import com.artlable.backend.feed.command.application.dto.read.FeedReadResponseDTO;

import com.artlable.backend.feed.command.application.dto.update.FeedUpdateRequestDTO;
import com.artlable.backend.feed.command.application.service.FeedService;
import com.artlable.backend.files.command.application.dto.feed.CreateFeedFileRequestDTO;
import com.artlable.backend.files.command.application.service.FileService;
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
@Api(tags= "FEED API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    @ApiOperation(value = "전체 피드 조회")
    @GetMapping("/feeds")
    public ResponseEntity<ResponseMessage> findAllFeeds() {

        try{
            Map<String, Object> responseMap = new HashMap<>();
            List<FeedReadResponseDTO> feeds = feedService.findAllFeeds();
            responseMap.put("feeds", feeds);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "전체피드 조회성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "피드 번호로 조회")
    @GetMapping("/feeds/{feedNo}")
    public ResponseEntity<ResponseMessage> findFeedById(@PathVariable Long feedNo) {

        try{
            Map<String, Object> responseMap = new HashMap<>();
            FeedReadResponseDTO feed = feedService.findFeed(feedNo);
            responseMap.put("feeds", feed);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "단일피드 조회성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "피드 작성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feed", value = "Feed JSON", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "files", value = "Files", dataType = "file", paramType = "form", allowMultiple = true) })
    @PostMapping(value = "/feeds", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseMessage> createFeed(
            @RequestBody FeedCreateRequestDTO requestDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestHeader("Authorization") String accessToken) {

        try {
            Long feedNo = feedService.createFeed(requestDTO, accessToken);
            List<CreateFeedFileRequestDTO> uploadedFiles = fileService.feedSaveFile(files, feedNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("feedNo", feedNo);
            responseMap.put("uploadedFiles", uploadedFiles);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpStatus.CREATED.value(), "피드작성 성공", responseMap));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }




    @ApiOperation(value = "피드 수정")
    @PutMapping("/feeds/{feedNo}")
    public ResponseEntity<ResponseMessage> modifyInfo(@PathVariable Long feedNo,
                                                      @RequestBody FeedUpdateRequestDTO requestDTO,
                                                      @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultFeedNo = feedService.updateFeed(feedNo,requestDTO, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("feedNo",resultFeedNo);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "피드수정 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }

    @ApiOperation(value = "피드 삭제")
    @DeleteMapping("/feeds/{feedNo}")
    public ResponseEntity<ResponseMessage> removeFeed(@PathVariable Long feedNo,
                                                      @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultFeedNo = feedService.deleteFeed(feedNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("feedNo",resultFeedNo);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpStatus.OK.value(), "피드삭제 성공",responseMap));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null));
        }
    }
}