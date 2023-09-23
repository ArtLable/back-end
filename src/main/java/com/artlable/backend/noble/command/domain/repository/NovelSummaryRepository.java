package com.artlable.backend.noble.command.domain.repository;

import com.artlable.backend.noble.command.domain.aggregate.entity.NovelSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelSummaryRepository extends JpaRepository<NovelSummary, Long> {

    List<NovelSummary> findBySummaryOrderBySummaryNoDesc();

    NovelSummary findBySummaryNo(Long summaryNo);
}
