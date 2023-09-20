package com.artlable.backend.feed.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.feed.command.application.dto.create.FeedCreateRequestDTO;
import com.artlable.backend.feed.command.application.dto.read.FeedReadResponseDTO;

import com.artlable.backend.feed.command.application.dto.update.FeedUpdateRequestDTO;
import com.artlable.backend.feed.command.application.service.FeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags= "FEED CRUD API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @ApiOperation(value = "전체 피드 조회")
    @GetMapping("/feeds")
    public ResponseEntity<?> findAllFeeds() {

        try{
            Map<String, Object> responseMap = new HashMap<>();
            List<FeedReadResponseDTO> feeds = feedService.findAllFeeds();
            responseMap.put("feeds", feeds);

            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "피드 번호로 조회")
    @GetMapping("/feeds/{feedNo}")
    public ResponseEntity<?> findFeedById(@PathVariable Long feedNo) {

        try{
            Map<String, Object> responseMap = new HashMap<>();
            FeedReadResponseDTO feed = feedService.findFeed(feedNo);
            responseMap.put("feeds", feed);

            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }

    }

    @ApiOperation(value = "피드 작성")
    @PostMapping("/feeds")
    public ResponseEntity<?> createFeed(@RequestBody FeedCreateRequestDTO requestDTO,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long feedNo = feedService.createFeed(requestDTO, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("feedNo",feedNo);
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "피드 수정")
    @PutMapping("/feeds/{feedNo}")
    public ResponseEntity<?> modifyInfo(@PathVariable Long feedNo,
                                        @RequestBody FeedUpdateRequestDTO requestDTO,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultFeedNo = feedService.updateFeed(feedNo,requestDTO, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("feedNo",resultFeedNo);
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @ApiOperation(value = "피드 삭제")
    @DeleteMapping("/feeds/{feedNo}")
    public ResponseEntity<?> removeFeed(@PathVariable Long feedNo,
                                        @RequestHeader("Authorization") String accessToken) {

        try {
            Long resultFeedNo = feedService.deleteFeed(feedNo, accessToken);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("feedNo",resultFeedNo);
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception e){
            ResponseMessage responseMessage =new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

}
