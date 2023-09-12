package com.artlable.backend.feed.command.domain.repository;

import com.artlable.backend.feed.command.application.dto.FeedSearchFilter;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {

    List<Feed> findFeedListBySearch(FeedSearchFilter feedSearchFilter);
}
