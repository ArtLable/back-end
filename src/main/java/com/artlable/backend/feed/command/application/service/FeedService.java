package com.artlable.backend.feed.command.application.service;

import com.artlable.backend.feed.command.application.dto.FeedDeleteDTO;
import com.artlable.backend.feed.command.application.dto.FeedListDTO;
import com.artlable.backend.feed.command.application.dto.FeedRegistDTO;
import com.artlable.backend.feed.command.application.dto.FeedUpdateDTO;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.feed.command.domain.repository.FeedMapper;
import com.artlable.backend.feed.command.domain.repository.FeedRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final FeedRepository feedRepository;
//    private final FeedMapper feedMapper;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;
    private Long feedNo;

    @Autowired
    public FeedService(FeedRepository feedRepository,  ModelMapper modelMapper,
                           ObjectMapper objectMapper) {
        this.feedRepository = feedRepository;
//        this.feedMapper = feedMapper;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    public List<FeedListDTO> findAllFeeds(Pageable pageable) {

        List<Feed> feeds = feedRepository.findAll(Sort.by(Sort.Direction.DESC, "feedNo"));

        return feeds.stream().map(feed -> modelMapper.map(feed, FeedListDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long registNewFeed(FeedRegistDTO newFeed, String auth) throws JsonProcessingException {
        Map<String, String> authMap = objectMapper.readValue(auth, Map.class);

        String id = String.valueOf(authMap.get("memberNo"));
        Long memberNo = Long.parseLong(id);

        if (auth.equals("")) {
            throw new IllegalArgumentException("비회원 접근");
        }

        newFeed.setMemberNo(memberNo);
        newFeed.setCreatedDate(new Date());

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
}
