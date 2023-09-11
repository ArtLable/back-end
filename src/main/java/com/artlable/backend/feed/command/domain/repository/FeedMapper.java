package com.artlable.backend.feed.command.domain.repository;


import com.artlable.backend.feed.command.application.dto.FeedSearchFilter;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FeedMapper {

    List<Feed> findFeedListBySearch(FeedSearchFilter feedSearchFilter);
}
