package com.artlable.backend.feed.command.application.controller;

import com.artlable.backend.common.ResponseMessage;
import com.artlable.backend.feed.command.application.dto.FeedRegistDTO;
import com.artlable.backend.feed.command.application.dto.FeedListDTO;

import com.artlable.backend.feed.command.application.dto.FeedUpdateDTO;
import com.artlable.backend.feed.command.application.service.FeedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }
    @ApiOperation(value = "모든 피드 조회")
    @GetMapping("/lists")
    public ResponseEntity<ResponseMessage> findAllFeeds(@PageableDefault(size = 1) Pageable pageable) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();

        List<FeedListDTO> lists = feedService.findAllFeeds((java.awt.print.Pageable) pageable);
        responseMap.put("lists", lists);

        return new ResponseEntity<>(
                new ResponseMessage(200, "조회성공", responseMap),
                headers,
                HttpStatus.OK
        );
    }
    @ApiOperation(value = "피드 작성")
    @PostMapping("/lists")
    public ResponseEntity<?> registFeed(FeedRegistDTO newFeed, @RequestHeader(value = "Auth") String auth)
        throws JsonProcessingException {

        Long feedNo = feedService.registNewFeed(newFeed, auth);

        return ResponseEntity
                .created(URI.create("/api/v1/lists/regist/" + newFeed.getFeedNo()))
                .build();
    }

    @ApiOperation(value = "피드 수정")
    @PutMapping("/feeds/{feedNo}")
    public ResponseEntity<?> modifyFeed(FeedUpdateDTO modifyFeed,
                                        @PathVariable Long feedNo) {
        feedService.modifyFeed(modifyFeed, feedNo);

        return ResponseEntity
                .created(URI.create("/api/v1/feeds/" + feedNo))
                .build();
    }

    @ApiOperation(value = "피드 삭제")
    @DeleteMapping("/feeds/{feedNo}")
    public ResponseEntity<?> removeFeed(@PathVariable Long feedNo) {

        feedService.removeFeed(feedNo);

        return ResponseEntity
                .noContent()
                .build();
    }
}
