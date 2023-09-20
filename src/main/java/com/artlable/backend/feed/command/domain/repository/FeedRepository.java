package com.artlable.backend.feed.command.domain.repository;


import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {


    //전체피드중 isDeleted false, feedno 내림차순 정렬
    List<Feed> findByFeedIsDeletedFalseOrderByFeedNoDesc();

    //피드 번호로 피드 조회
    Feed findByFeedNo(Long feedNo);
}
