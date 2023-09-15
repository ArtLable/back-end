package com.artlable.backend.feed.command.domain.repository;

import com.artlable.backend.feed.command.application.dto.FeedSearchFilter;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
