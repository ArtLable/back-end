package com.artlable.backend.novel.command.domain.repository;

import com.artlable.backend.novel.command.domain.aggregate.entity.NovelSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelSummaryRepository extends JpaRepository<NovelSummary, Long> {

    List<NovelSummary> findBySummaryIsDeletedFalseOrderBySummaryNoDesc();

    NovelSummary findBySummaryNo(Long summaryNo);
}
