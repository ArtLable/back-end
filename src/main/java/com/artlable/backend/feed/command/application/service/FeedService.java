package com.artlable.backend.feed.command.application.service;

import com.artlable.backend.feed.command.application.dto.*;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.feed.command.domain.repository.FeedRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FeedService(FeedRepository feedRepository, ModelMapper modelMapper) {
        this.feedRepository = feedRepository;
        this.modelMapper = modelMapper;
    }

    /* 전체 조회 */
    public List<FeedListDTO> findAllFeeds(Pageable pageable) {

        List<Feed> feeds = feedRepository.findAll(Sort.by(Sort.Direction.DESC, "feedNo"));

        return feeds.stream().map(feed -> modelMapper.map(feed, FeedListDTO.class))
                .collect(Collectors.toList());
    }

    /* 일부 조회 */
    public FeedListDTO findFeedById(Long feedNo) {

        Feed foundFeed = feedRepository.findById(feedNo).get();

        return modelMapper.map(foundFeed, FeedListDTO.class);
    }

    @Transactional
    public Long registNewFeed(FeedRegistDTO newFeed, String auth) throws JsonProcessingException { // 자바 객체를 json 문자열로 변환

//        if (auth.equals("")) {
//            throw new IllegalArgumentException("비회원 접근");
//        }

        newFeed.setFeedContent(newFeed.getFeedContent());
        newFeed.setFeedCategory(newFeed.getFeedCategory());

        return feedRepository.save(modelMapper.map(newFeed, Feed.class)).getFeedNo();
    }

    @Transactional
    public void modifyFeed(FeedUpdateDTO modifyFeed, Long feedNo) {

        Feed foundFeed = feedRepository.findById(feedNo).get();

        foundFeed.setFeedContent(modifyFeed.getFeedContent());
        foundFeed.setFeedCategory(modifyFeed.getFeedCategory());
    }

    @Transactional
    public void removeFeed(Long feedNo) {

        Feed foundFeed = feedRepository.findById(feedNo).get();
        feedRepository.delete(foundFeed);

        FeedDeleteDTO feedDeleteDTO = new FeedDeleteDTO();
    }

//    public List<FeedListDTO> findFeedListBySearch(FeedSearchFilter feedSearchFilter) {
//
////        List<Feed> feeds = feedRepository.findFeedListBySearch(feedSearchFilter);
//
////        return feeds.stream().map(feed -> modelMapper.map(feed, FeedListDTO.class))
////                .collect(Collectors.toList());
////    }
}
