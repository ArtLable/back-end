package com.artlable.backend.webtoon.command.domain.repository;

import com.artlable.backend.webtoon.command.domain.aggregate.entity.Learning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRepository extends JpaRepository<Learning, Long> {

    List<Learning> findByLearningIsDeletedFalseOrderByLearningNoDesc();

    Learning findByLearningNo(Long learningNo);
}
